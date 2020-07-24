package com.github.chMatvey.springConcepts.car;

import com.github.chMatvey.springConcepts.afterInit.AfterInit;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Car {
    private String model;

    private Owner owner;

    @PostConstruct
    public void init() {
        model = "BMW";
    }

    @AfterInit
    public void afterInit() {
        System.out.println(String.format("I belong to %s", this.owner.getName()));
    }

    public String getModel() {
        return model;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
