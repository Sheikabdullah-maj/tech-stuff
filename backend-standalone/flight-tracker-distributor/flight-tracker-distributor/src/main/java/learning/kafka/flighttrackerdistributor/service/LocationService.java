package learning.kafka.flighttrackerdistributor.service;

import learning.kafka.flighttrackerdistributor.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedRate = 2000L)
    public void updateLocationPeriodically(){
        String randomCoords = Math.random() + "," + Math.random();
        System.out.println("Random Coordinates created !!"+ randomCoords);
        kafkaTemplate.send(CommonConstants.FLIGHT_TRACKER_TOPIC_NAME,randomCoords);
    }
}
