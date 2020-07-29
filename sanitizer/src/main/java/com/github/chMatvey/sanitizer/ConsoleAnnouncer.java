package com.github.chMatvey.sanitizer;

import com.github.chMatvey.sanitizer.infrastructure.configurator.object.InjectObject;

public class ConsoleAnnouncer implements Announcer {

    @InjectObject
    private Advertiser advertiser;

    @Override
    public void announce(String message) {
        System.out.println(message);
        advertiser.recommend();
    }
}
