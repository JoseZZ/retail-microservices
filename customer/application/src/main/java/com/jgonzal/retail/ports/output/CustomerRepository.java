package com.jgonzal.retail.ports.output;

import java.util.List;

import com.jgonzal.retail.model.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer findById(Long id);
    List<Customer> findAll();
    void deleteById(Long id);
}
