package com.github.chMatvey.sanitizer;

public class SeniorPoliceman implements Policeman {
    @Override
    public void getPeopleOut() {
        System.out.println("Please leave the room or we will have to take action");
    }
}
