package com.github.chMatvey.sanitizer;

import com.github.chMatvey.sanitizer.infrastructure.Application;
import com.github.chMatvey.sanitizer.infrastructure.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run(
                "com.github.chMatvey.sanitizer",
                new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class))
        );
        Sanitizer sanitizer = context.getObject(Sanitizer.class);
        sanitizer.start(new Room());
    }
}
