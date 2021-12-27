package com.github.chMatvey.customer.controller;

import com.github.chMatvey.customer.CustomerApplication;
import com.github.chMatvey.customer.controller.request.CustomerRegistrationRequest;
import com.github.chMatvey.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.github.chMatvey.customer.TestUtil.convertObjectToJsonBytes;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerApplication.class)
class CustomerControllerTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CustomerController customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Test
    void register() throws Exception {
        CustomerRegistrationRequest requestBody =
                new CustomerRegistrationRequest("customer", "customer", "customer@test.com");
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/customer", requestBody)
                .contentType(APPLICATION_JSON)
                .content(convertObjectToJsonBytes(requestBody));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}