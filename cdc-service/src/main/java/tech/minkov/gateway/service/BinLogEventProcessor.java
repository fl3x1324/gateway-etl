package tech.minkov.gateway.service;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.springframework.stereotype.Service;
import tech.minkov.data.MutationType;

@Service
public class BinLogEventProcessor {

    private final DataMutationEventGenerator eventGenerator;

    public BinLogEventProcessor(DataMutationEventGenerator eventGenerator) {
        this.eventGenerator = eventGenerator;
    }

    public void publishToMessageBroker(Event event) {
        switch (event.getHeader().getEventType()) {
            case TABLE_MAP:
                var tableMapEvent = (TableMapEventData) event.getData();
                eventGenerator.saveTableInfo(
                    tableMapEvent.getDatabase(),
                    tableMapEvent.getTable()
                );
                break;
            case EXT_WRITE_ROWS:
                var writeEvent = (WriteRowsEventData) event.getData();
                eventGenerator.saveDataMutationInfo(MutationType.INSERTION,
                    writeEvent.getRows(), false);
                eventGenerator.emitEvent();
                break;
            case EXT_UPDATE_ROWS:
                var updateEvent = (UpdateRowsEventData) event.getData();
                eventGenerator.saveDataMutationInfo(MutationType.UPDATE,
                    updateEvent.getRows());
                eventGenerator.emitEvent();
                break;
            case EXT_DELETE_ROWS:
                var deleteEvent = (DeleteRowsEventData) event.getData();
                eventGenerator.saveDataMutationInfo(MutationType.DELETION,
                    deleteEvent.getRows(), false);
                eventGenerator.emitEvent();
                break;
            default:
                break;
        }
    }
}
