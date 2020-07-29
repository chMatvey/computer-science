package com.github.chMatvey.sanitizer;

import com.github.chMatvey.sanitizer.infrastructure.Singleton;
import com.github.chMatvey.sanitizer.infrastructure.configurator.object.InjectProperty;

@Singleton
public class AdvertiserImpl implements Advertiser {
    @InjectProperty
    private String drink;

    public AdvertiserImpl() {
        System.out.println("Advertiser was created");
    }

    @Override
    public void recommend() {
        System.out.println(String.format("To be healthy drink %s", drink));
    }
}
