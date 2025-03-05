package com.jgonzal.retail.ports.output;

import java.util.List;
import java.util.Optional;
import com.jgonzal.retail.model.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    boolean deleteById(Long id);
    Optional<Customer> update(Customer customer);
}
