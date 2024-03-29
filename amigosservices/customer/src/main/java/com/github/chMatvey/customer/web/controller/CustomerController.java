package com.github.chMatvey.customer.web.controller;

import com.github.chMatvey.customer.service.CustomerService;
import com.github.chMatvey.customer.web.controller.request.CustomerRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService customerService) {
    @PostMapping
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        log.info("New customer registration {}", request);
        customerService.register(request);
        return noContent().build();
    }
}
