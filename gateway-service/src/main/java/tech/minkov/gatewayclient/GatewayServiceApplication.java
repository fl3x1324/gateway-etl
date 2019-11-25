package tech.minkov.gatewayclient;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@SpringBootApplication
public class GatewayServiceApplication {

    private static final Logger logger = LoggerFactory
        .getLogger(GatewayServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @KafkaListener(topics = {"tableMap", "extWriteRow", "extUpdateRow"})
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
    }
}
