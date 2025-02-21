package com.retail.customer.domain.repository;

import com.retail.customer.domain.model.Customer;
import java.util.List;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer findById(Long id);
    List<Customer> findAll();
    void deleteById(Long id);
}
