package com.github.chMatvey.customer.service;

import com.github.chMatvey.customer.controller.request.CustomerRegistrationRequest;
import com.github.chMatvey.customer.model.Customer;
import com.github.chMatvey.customer.repository.CustomerRepository;
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
