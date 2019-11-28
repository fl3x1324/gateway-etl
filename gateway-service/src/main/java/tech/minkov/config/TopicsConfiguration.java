package tech.minkov.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicsConfiguration {

    @Bean
    public NewTopic recordInsertTopic() {
        return TopicBuilder.name("record_insert").partitions(1).replicas(1)
            .build();
    }

    @Bean
    public NewTopic recordUpdateTopic() {
        return TopicBuilder.name("record_update").partitions(1).replicas(1)
            .build();
    }

    @Bean
    public NewTopic recordDeleteTopic() {
        return TopicBuilder.name("record_delete").partitions(1).replicas(1)
            .build();
    }
}
