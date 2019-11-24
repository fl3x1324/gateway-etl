package tech.minkov.gateway.service;

import com.github.shyiko.mysql.binlog.event.Event;
import kafka.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BinLogEventProcessor {

    private final KafkaTemplate<String, String> template;

    public BinLogEventProcessor(
        KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void publishToMessageBroker(Event event) {
        switch (event.getHeader().getEventType()) {
            case TABLE_MAP:
                template.send(Topic.TABLE_MAP.toString(),
                    event.getData().toString());
                break;
            case EXT_WRITE_ROWS:
                template.send(Topic.EXT_WRITE_ROW.value,
                    event.getData().toString());
                break;
            case EXT_UPDATE_ROWS:
                template.send(Topic.EXT_UPDATE_ROW.value,
                    event.getData().toString());
        }
    }
}
