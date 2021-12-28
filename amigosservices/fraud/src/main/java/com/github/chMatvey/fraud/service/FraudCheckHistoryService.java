package com.github.chMatvey.fraud.service;

import com.github.chMatvey.fraud.model.FraudCheckHistory;
import com.github.chMatvey.fraud.repository.FraudCheckHistoryRepository;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public record FraudCheckHistoryService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
    public boolean isFraudulentCustomer(Long customerId) {
        FraudCheckHistory fraudCheckHistory = fraudCheckHistoryRepository.findByCustomerId(customerId)
                .orElseGet(() -> fraudCheckHistoryRepository.save(createNew(customerId)));

        return fraudCheckHistory.getIsFraudster();
    }

    private FraudCheckHistory createNew(Long customerId) {
        return FraudCheckHistory.builder()
                .isFraudster(false)
                .customerId(customerId)
                .createdAt(now())
                .build();
    }
}
