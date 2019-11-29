package tech.minkov.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import tech.minkov.data.DataMutationEventInfo;

@Configuration
public class KafkaListenerConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"record_insert", "record_update", "record_delete"})
    public void listen(ConsumerRecord<String, DataMutationEventInfo> cr) {
        var dataChangeEvent = cr.value();
        logger.info("{}", dataChangeEvent);
    }
}
