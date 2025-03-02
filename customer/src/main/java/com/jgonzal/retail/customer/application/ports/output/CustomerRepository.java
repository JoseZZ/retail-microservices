package com.jgonzal.retail.customer.application.ports.output;

import java.util.List;

import com.jgonzal.retail.customer.domain.entities.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer findById(Long id);
    List<Customer> findAll();
    void deleteById(Long id);
}
