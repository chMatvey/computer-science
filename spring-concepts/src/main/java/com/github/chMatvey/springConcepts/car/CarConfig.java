package com.github.chMatvey.springConcepts.car;

import com.github.chMatvey.springConcepts.infra.EnableAfterInit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAfterInit
public class CarConfig {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarConfig.class);
    }
}
