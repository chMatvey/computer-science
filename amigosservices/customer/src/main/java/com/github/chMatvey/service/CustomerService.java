package com.github.chMatvey.service;

import com.github.chMatvey.controller.request.CustomerRegistrationRequest;
import com.github.chMatvey.model.Customer;
import com.github.chMatvey.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void register(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo check is email valid
        // todo check email not taken
        customerRepository.save(customer);
    }
}
