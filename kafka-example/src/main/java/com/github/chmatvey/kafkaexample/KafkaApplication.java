package com.github.chmatvey.kafkaexample;

import com.github.chmatvey.kafkaexample.config.KafkaTopicConfig;
import com.github.chmatvey.kafkaexample.model.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import static java.time.LocalDateTime.now;

@SpringBootApplication
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, Message> kafkaTemplate) {
        return args -> {
            for (int i = 0; i < 100; i++) {
                Message message = new Message("CMD", "Hello Kafka :) " + i, now());
                kafkaTemplate.send(KafkaTopicConfig.TOPIC_NAME, message);
            }
        };
    }
}
