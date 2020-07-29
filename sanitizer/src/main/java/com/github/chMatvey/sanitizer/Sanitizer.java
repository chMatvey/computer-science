package com.github.chMatvey.sanitizer;

import com.github.chMatvey.sanitizer.infrastructure.configurator.object.InjectObject;

public class Sanitizer {

    @InjectObject
    private Announcer announcer;

    @InjectObject
    private Policeman policeman;

    public void start(Room room) {
        announcer.announce("Disinfection starting, please leave the room");
        policeman.getPeopleOut();
        disinfect(room);
        announcer.announce("You can go back");
    }

    private void disinfect(Room room) {
        System.out.println("The room is being disinfected");
    }
}
