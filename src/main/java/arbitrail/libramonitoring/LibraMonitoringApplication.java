package arbitrail.libramonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LibraMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraMonitoringApplication.class, args);
	}
}
