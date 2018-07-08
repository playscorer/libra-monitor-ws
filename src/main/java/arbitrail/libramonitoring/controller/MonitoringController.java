package arbitrail.libramonitoring.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class MonitoringController {
	
	private final static Logger LOG = Logger.getLogger(MonitoringController.class);
	
	private static int START_LINE = 15;

	@Value("${logdirpath}")
	private String LOGDIRPATH;
	
    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay=10000)
    public void publishUpdate() {
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path rootPath = Paths.get(LOGDIRPATH);
			rootPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey key;
			if ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					Path logFilePath = (Path) event.context();
					LOG.debug("Event kind:" + event.kind() + ". File affected: " + logFilePath + ".");
					sendFile(rootPath.resolve(logFilePath));
				}
				key.reset();
			}
		} catch (Exception e) {
			LOG.error("Unexpected Exception :", e);
		}
    }
    
	private void sendFile(Path filePath) {
		int nbLine = 0;
		Charset charset = Charset.forName("US-ASCII");

		try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	if (nbLine++ >= START_LINE) {
		    		template.convertAndSend("/topic/logs", line); 
		    	}
		    }
		    
		} catch (IOException ioe) {
			LOG.error("IOException :", ioe);
		}
	}
    
}
