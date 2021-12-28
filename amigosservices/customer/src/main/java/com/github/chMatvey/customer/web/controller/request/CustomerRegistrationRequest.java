package com.github.chMatvey.customer.web.controller.request;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {}
