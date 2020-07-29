package com.github.chMatvey.sanitizer;

import com.github.chMatvey.sanitizer.infrastructure.configurator.object.InjectObject;

import javax.annotation.PostConstruct;

@Deprecated
public class PolicemanImpl implements Policeman {

    @InjectObject
    private Advertiser advertiser;

    @PostConstruct
    public void init() {
        System.out.println(advertiser.getClass());
    }

    @Override
    public void getPeopleOut() {
        System.out.println("You must leave the room or you will be arrested");
    }
}
