package tech.minkov.gatewayclient.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicsConfiguration {

    @Bean
    public NewTopic tableMapTopic() {
        return TopicBuilder.name("tableMap").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic extWriteRows() {
        return TopicBuilder.name("extWriteRow").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic extUpdateRows() {
        return TopicBuilder.name("extUpdateRow").partitions(1).replicas(1).build();
    }
}
