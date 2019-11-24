package tech.minkov.gateway.config;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.minkov.gateway.service.BinLogEventProcessor;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Value("${gatewayapp.dbhost}")
    private String dbHost;
    @Value("${gatewayapp.dbport}")
    private int dbPort;
    @Value("${gatewayapp.dbsuperuser}")
    private String dbSuperUser;
    @Value("${gatewayapp.dbsuperuserpassword}")
    private String dbSuperUserPassword;
    private static final Logger LOG =
        LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    private final BinLogEventProcessor binLogEventProcessor;

    public CommandLineAppStartupRunner(
        BinLogEventProcessor binLogEventProcessor) {
        this.binLogEventProcessor = binLogEventProcessor;
    }

    @Override
    public void run(String... args) {
        var client = new BinaryLogClient(
            dbHost,
            dbPort,
            dbSuperUser,
            dbSuperUserPassword
        );
        var eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
            EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(
            binLogEventProcessor::publishToMessageBroker
        );
        new Thread(() -> {
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "binlog-client").start();
    }
}
