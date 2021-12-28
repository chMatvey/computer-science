package com.github.chMatvey.fraud.web.controller;

import com.github.chMatvey.fraud.service.FraudCheckHistoryService;
import com.github.chMatvey.fraud.web.controller.request.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudCheckHistoryController(FraudCheckHistoryService fraudCheckHistoryService) {
    @PutMapping(path = "{customerId}")
    public ResponseEntity<FraudCheckResponse> isFraudster(@PathVariable Long customerId) {
        boolean isFraudulentCustomer = fraudCheckHistoryService.isFraudulentCustomer(customerId);
        log.info("Fraud check request for customer {}", customerId);

        return ResponseEntity.ok(new FraudCheckResponse(isFraudulentCustomer));
    }
}
