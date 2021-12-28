package com.github.chMatvey.fraud.repository;

import com.github.chMatvey.fraud.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, Long> {
    Optional<FraudCheckHistory> findByCustomerId(Long customerId);
}
