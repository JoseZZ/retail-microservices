package com.jgonzal.retail.customer.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;

import com.jgonzal.retail.customer.application.ports.output.CustomerRepository;
import com.jgonzal.retail.customer.domain.entities.Customer;
import com.jgonzal.retail.customer.infrastructure.repository.JpaCustomerRepository;

import java.util.List;

@Component
public class SqlCustomerRepository implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    public SqlCustomerRepository(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return jpaCustomerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return jpaCustomerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }
}