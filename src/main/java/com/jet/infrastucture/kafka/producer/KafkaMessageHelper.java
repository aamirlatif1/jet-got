package com.jet.infrastucture.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaMessageHelper {

    public <K, V> BiConsumer<? super SendResult<K, V>, ? super Throwable> callback() {
        return (result, ex) -> {
            if (ex != null) {
                log.error("Error while sending message ", ex);
            } else {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for" +
                                " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            }
        };
    }
}