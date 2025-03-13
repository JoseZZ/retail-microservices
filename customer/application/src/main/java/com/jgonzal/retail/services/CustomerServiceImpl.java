package com.jgonzal.retail.services;

import org.springframework.stereotype.Service;

import com.jgonzal.retail.model.Customer;
import com.jgonzal.retail.ports.input.CustomerService;
import com.jgonzal.retail.ports.output.CustomerRepository;
import com.jgonzal.retail.exception.CustomerNotFoundException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        customer.validate();
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) {
        if(!customerRepository.deleteById(id)){
            throw new CustomerNotFoundException(id);
        }
    }

    public Customer updateCustomer(Long id, Customer customer) {
        customer.validate();
        return customerRepository.update(customer).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}