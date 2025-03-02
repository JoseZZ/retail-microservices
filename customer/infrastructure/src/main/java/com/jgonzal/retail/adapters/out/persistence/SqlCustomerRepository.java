package com.jgonzal.retail.adapters.out.persistence;

import org.springframework.stereotype.Component;

import com.jgonzal.retail.model.Customer;
import com.jgonzal.retail.ports.output.CustomerRepository;
import com.jgonzal.retail.adapters.out.persistence.mapper.CustomerMapper;
import com.jgonzal.retail.adapters.out.persistence.repository.JpaCustomerRepository;

import java.util.List;

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
    public Customer findById(Long id) {
        var customerEntity = jpaCustomerRepository.findById(id).orElse(null);
        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public List<Customer> findAll() {
        var customerEntities = jpaCustomerRepository.findAll();
        return customerMapper.toDomainList(customerEntities);
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }
}