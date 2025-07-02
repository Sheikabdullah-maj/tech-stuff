package learning.kafka.flighttrackerconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LocationListenerService {

    @KafkaListener(topics = "flight-location-tracker", groupId = "listener-group")
    public void getLocation(String location) {
        System.out.println("location = "+location);
    }
}
