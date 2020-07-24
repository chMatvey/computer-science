package com.github.chMatvey.springConcepts.car;

import com.github.chMatvey.springConcepts.afterInit.AfterInit;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Owner {
    private String name;

    private final Car car;

    public Owner(Car car) {
        this.car = car;
    }

    @PostConstruct
    public void init() {
        name = "Alex";
        car.setOwner(this);
    }

    @AfterInit
    public void afterInit() {
        System.out.println(String.format("I have %s car", this.car.getModel()));
    }

    public String getName() {
        return name;
    }
}
