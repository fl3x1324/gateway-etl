package tech.minkov.gateway.service;

import com.github.shyiko.mysql.binlog.event.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProcessor {

    private final KafkaTemplate<String, String> template;

    public EventProcessor(
        KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void publish(Event event) {
        switch (event.getHeader().getEventType()) {
            case TABLE_MAP:
                template.send("tableMap", event.getData().toString());
                break;
            case EXT_WRITE_ROWS:
                template.send("extWriteRow", event.getData().toString());
                break;
            case EXT_UPDATE_ROWS:
                template.send("extUpdateRow", event.getData().toString());
        }
    }
}
