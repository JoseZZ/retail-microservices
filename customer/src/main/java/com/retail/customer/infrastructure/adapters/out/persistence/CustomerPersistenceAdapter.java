package com.retail.customer.infrastructure.adapters.out.persistence;

import com.retail.customer.domain.model.Customer;
import com.retail.customer.domain.repository.CustomerRepository;
import com.retail.customer.infrastructure.repository.JpaCustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerPersistenceAdapter implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;

    public CustomerPersistenceAdapter(JpaCustomerRepository jpaCustomerRepository) {
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