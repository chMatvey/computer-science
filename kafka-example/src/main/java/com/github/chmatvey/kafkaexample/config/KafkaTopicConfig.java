package com.github.chmatvey.kafkaexample.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String TOPIC_NAME = "kafka-topic";

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(TOPIC_NAME)
                .build();
    }
}
