package com.github.chMatvey.springBootStarter.starter;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class StartListener {

    @EventListener
    public void start(ContextRefreshedEvent event) {
        System.out.println("Application started");
    }
}
