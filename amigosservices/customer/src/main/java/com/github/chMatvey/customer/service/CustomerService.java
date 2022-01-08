package com.github.chMatvey.customer.service;

import com.github.chMatvey.clients.fraud.FraudCheckResponse;
import com.github.chMatvey.clients.fraud.FraudClient;
import com.github.chMatvey.customer.model.Customer;
import com.github.chMatvey.customer.repository.CustomerRepository;
import com.github.chMatvey.customer.web.controller.request.CustomerRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;

    public void register(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check is email valid
        // todo: check email not taken
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse == null || fraudCheckResponse.isFraudster() == null) {
            throw new IllegalStateException("Maybe fraudster. Try later");
        }
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
        // todo: send notification
    }
}
