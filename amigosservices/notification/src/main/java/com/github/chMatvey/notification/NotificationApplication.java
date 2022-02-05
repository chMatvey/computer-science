package com.github.chMatvey.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(
        scanBasePackages = {
                "com.github.chMatvey.notification",
                "com.github.chMatvey.amqp"
        }
)
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(RabbitMqMessageProducer messageProducer,
//                                        NotificationConfig notificationConfig) {
//        return args -> {
//            messageProducer.publish(
//                    new Person("Alex", 25),
//                    notificationConfig.getInternalExchange(),
//                    notificationConfig.getInternalNotificationRoutingKey()
//            );
//        };
//    }
//
//    record Person(String name, int age) {}
}
