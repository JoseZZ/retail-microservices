package com.jgonzal.retail.customer.application.ports.output;

import java.util.List;
import com.jgonzal.retail.customer.infrastructure.adapters.out.persistence.entity.CustomerEntity;

public interface CustomerRepository {
    CustomerEntity save(CustomerEntity customer);
    CustomerEntity findById(Long id);
    List<CustomerEntity> findAll();
    void deleteById(Long id);
}
