package com.github.chMatvey.customer.service;

import com.github.chMatvey.customer.service.response.FraudCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FraudService {
    private final RestTemplate restTemplate;

    public FraudCheckResponse fraudCheck(Long customerId) {
        return restTemplate.exchange(
                "http://localhost:9000/api/v1/fraud-check/{customerId}",
                HttpMethod.PUT,
                null,
                FraudCheckResponse.class,
                customerId
        ).getBody();
    }
}
