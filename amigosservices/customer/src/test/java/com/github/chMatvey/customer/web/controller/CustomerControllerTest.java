package com.github.chMatvey.customer.web.controller;

import com.github.chMatvey.clients.fraud.FraudCheckResponse;
import com.github.chMatvey.clients.fraud.FraudClient;
import com.github.chMatvey.customer.CustomerApplication;
import com.github.chMatvey.customer.service.CustomerService;
import com.github.chMatvey.customer.web.controller.request.CustomerRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.github.chMatvey.customer.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerApplication.class)
class CustomerControllerTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private FraudClient fraudService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CustomerController customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
        when(fraudService.isFraudster(anyLong()))
                .thenReturn(new FraudCheckResponse(false));
    }

    @Test
    void register() throws Exception {
        CustomerRegistrationRequest requestBody =
                new CustomerRegistrationRequest("customer", "customer", "customer@test.com");
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/customer", requestBody)
                .contentType(APPLICATION_JSON)
                .content(convertObjectToJsonBytes(requestBody));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }
}