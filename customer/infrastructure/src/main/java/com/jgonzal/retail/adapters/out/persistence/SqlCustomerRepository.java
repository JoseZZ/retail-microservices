package com.jgonzal.retail.adapters.out.persistence;

import org.springframework.stereotype.Component;

import com.jgonzal.retail.model.Customer;
import com.jgonzal.retail.ports.output.CustomerRepository;
import com.jgonzal.retail.adapters.out.persistence.mapper.CustomerMapper;
import com.jgonzal.retail.adapters.out.persistence.repository.JpaCustomerRepository;
import com.jgonzal.retail.exception.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class SqlCustomerRepository implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    public SqlCustomerRepository(JpaCustomerRepository jpaCustomerRepository, CustomerMapper customerMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer save(Customer customer) {
        var customerEntity = customerMapper.toEntity(customer);
        jpaCustomerRepository.save(customerEntity);
        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id).map(customerMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        var customerEntities = jpaCustomerRepository.findAll();
        return customerMapper.toDomainList(customerEntities);
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpaCustomerRepository.existsById(id)) {
            jpaCustomerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override   
    public Optional<Customer> update(Customer customer) {
        if (jpaCustomerRepository.existsById(customer.getId())) {
            var customerEntity = customerMapper.toEntity(customer);
            var updatedCustomer = jpaCustomerRepository.save(customerEntity);
            return Optional.of(customerMapper.toDomain(updatedCustomer));
        }
        return Optional.empty();
    }
}