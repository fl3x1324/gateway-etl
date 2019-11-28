package tech.minkov.gateway.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import tech.minkov.data.DataMutationEventInfo;
import tech.minkov.data.MutationType;
import tech.minkov.kafka.Topic;

@Service
public class DataMutationEventGenerator {

    private final KafkaTemplate<String, DataMutationEventInfo> template;
    private DataMutationEventInfo dataMutationEvent;

    public DataMutationEventGenerator(
        KafkaTemplate<String, DataMutationEventInfo> template) {
        this.template = template;
    }

    public void saveTableInfo(String database, String table) {
        this.dataMutationEvent = new DataMutationEventInfo();
        this.dataMutationEvent.setDatabase(database);
        this.dataMutationEvent.setTable(table);
    }

    public void saveDataMutationInfo(MutationType mutationType,
        List<Entry<Serializable[], Serializable[]>> rows) {
        this.dataMutationEvent.setType(mutationType);
        this.dataMutationEvent.setObjectIds(new ArrayList<>());
        rows.forEach(
            row -> dataMutationEvent.getObjectIds()
                .add(Long.parseLong(row.getValue()[0].toString()))
        );
    }

    public void saveDataMutationInfo(MutationType mutationType,
        List<Serializable[]> rows, boolean unused) {
        this.dataMutationEvent.setType(mutationType);
        this.dataMutationEvent.setObjectIds(new ArrayList<>());
        rows.forEach(
            row -> dataMutationEvent.getObjectIds()
                .add(Long.parseLong(row[0].toString()))
        );
    }

    public ListenableFuture<SendResult<String, DataMutationEventInfo>> emitEvent() {
        return template.send(
            Topic.fromDataMutationType(this.dataMutationEvent.getType())
                .toString(), this.dataMutationEvent
        );
    }
}
