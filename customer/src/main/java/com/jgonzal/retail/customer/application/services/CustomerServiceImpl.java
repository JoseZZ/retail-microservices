package com.jgonzal.retail.customer.application.services;

import org.springframework.stereotype.Service;

import com.jgonzal.retail.customer.application.ports.input.CustomerService;
import com.jgonzal.retail.customer.application.ports.output.CustomerRepository;
import com.jgonzal.retail.customer.domain.model.Customer;
import com.jgonzal.retail.customer.infrastructure.adapters.out.persistence.mapper.CustomerMapper;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public Customer createCustomer(Customer customer) {
        var customerEntity = customerMapper.toEntity(customer);
        var savedEntity = customerRepository.save(customerEntity);
        return customerMapper.toDomain(savedEntity);
    }

    public Customer getCustomerById(Long id) {
        var customerEntity = customerRepository.findById(id);
        return customerMapper.toDomain(customerEntity);
    }

    public List<Customer> getAllCustomers() {
        var customerEntities = customerRepository.findAll();
        return customerMapper.toDomainList(customerEntities);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}