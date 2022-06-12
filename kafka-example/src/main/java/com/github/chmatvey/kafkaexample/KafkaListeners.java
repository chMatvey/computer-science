package com.github.chmatvey.kafkaexample;

import com.github.chmatvey.kafkaexample.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    @KafkaListener(
            topics = "kafka-topic",
            groupId = "groupId",
            containerFactory = "messageFactory"
    )
    void listener(Message data) {
        log.info("Listener received: {} 🎉", data);
    }
}
