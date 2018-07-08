package arbitrail.libramonitoring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import arbitrail.libramonitoring.service.LogFileService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraMonitoringApplicationTests {

	@Autowired
	private LogFileService logFileService;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testWatchDirectory() {
		//logFileService.watchDirectory();
	}

}
