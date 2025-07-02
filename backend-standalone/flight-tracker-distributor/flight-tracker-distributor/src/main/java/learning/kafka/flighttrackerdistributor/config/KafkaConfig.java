package learning.kafka.flighttrackerdistributor.config;

import learning.kafka.flighttrackerdistributor.constants.CommonConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic flightLocationTrackerTopic() {
        return TopicBuilder.name(CommonConstants.FLIGHT_TRACKER_TOPIC_NAME).build();
    }
}
