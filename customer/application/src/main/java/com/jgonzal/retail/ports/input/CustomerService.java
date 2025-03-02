package com.jgonzal.retail.ports.input;

import java.util.List;

import com.jgonzal.retail.model.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    void deleteCustomer(Long id);
} 