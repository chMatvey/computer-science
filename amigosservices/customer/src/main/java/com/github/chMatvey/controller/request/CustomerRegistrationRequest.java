package com.github.chMatvey.controller.request;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {}
