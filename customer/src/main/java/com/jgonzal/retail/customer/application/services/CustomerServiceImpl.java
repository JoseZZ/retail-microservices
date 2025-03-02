package com.jgonzal.retail.customer.application.services;

import org.springframework.stereotype.Service;

import com.jgonzal.retail.customer.application.ports.input.CustomerService;
import com.jgonzal.retail.customer.application.ports.output.CustomerRepository;
import com.jgonzal.retail.customer.domain.entities.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}