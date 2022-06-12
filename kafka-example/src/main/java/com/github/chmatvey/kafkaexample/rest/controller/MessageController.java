package com.github.chmatvey.kafkaexample.rest.controller;

import com.github.chmatvey.kafkaexample.config.KafkaTopicConfig;
import com.github.chmatvey.kafkaexample.model.Message;
import com.github.chmatvey.kafkaexample.rest.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping
    public void publish(@RequestBody MessageRequest messageRequest) {
        log.info("REST request to publish message {}", messageRequest);
        Message message = new Message("REST API", messageRequest.message(), now());
        kafkaTemplate.send(KafkaTopicConfig.TOPIC_NAME, message);
    }
}
