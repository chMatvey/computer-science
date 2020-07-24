package com.github.chMatvey.springConcepts.car;

import com.github.chMatvey.springConcepts.afterInit.AfterInit;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final Car car;

    public CarService(Car car) {
        this.car = car;
    }

    @AfterInit
    public void afterInit() {
        System.out.println("Started working");
        System.out.println(String.format("Registered %s car with owner %s", car.getModel(), car.getOwner().getName()));
    }
}
