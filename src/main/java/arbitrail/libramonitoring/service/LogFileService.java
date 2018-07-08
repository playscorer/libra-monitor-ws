package arbitrail.libramonitoring.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LogFileService {
	private final static Logger LOG = Logger.getLogger(LogFileService.class);
	
	private static int START_LINE = 15;

	public String parseLogFile(Path filePath) {
		int nbLine = 0;
		StringBuilder stringBuilder = new StringBuilder();
		Charset charset = Charset.forName("US-ASCII");

		try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	if (nbLine++ >= START_LINE) {
		    		stringBuilder.append(line);
		    	}
		    }
		    
		    return stringBuilder.toString();
		} catch (IOException ioe) {
			LOG.error("IOException :", ioe);
			return null;
		}
	}

}
