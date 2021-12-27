package com.github.chMatvey.customer.repository;

import com.github.chMatvey.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
