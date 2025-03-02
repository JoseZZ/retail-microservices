package com.jgonzal.retail.customer.infrastructure.adapters.out.persistence.mapper;

import org.mapstruct.Mapper;

import com.jgonzal.retail.customer.domain.model.Customer;
import com.jgonzal.retail.customer.infrastructure.adapters.out.persistence.entity.CustomerEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    CustomerEntity toEntity(Customer customer);
    
    Customer toDomain(CustomerEntity customerEntity);
    
    List<Customer> toDomainList(List<CustomerEntity> customerEntities);
} 