package com.jet.infrastructure.kafka.producer;

import org.springframework.kafka.support.SendResult;
import java.io.Serializable;
import java.util.function.BiConsumer;

public interface KafkaProducer<K extends Serializable, V> {
    public void send(String topicName, K key, V message, BiConsumer<? super SendResult<K, V>, ? super Throwable> action);
}
