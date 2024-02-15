package learning.kafka.flighttrackerdistributor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightTrackerDistributorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightTrackerDistributorApplication.class, args);
	}

}
