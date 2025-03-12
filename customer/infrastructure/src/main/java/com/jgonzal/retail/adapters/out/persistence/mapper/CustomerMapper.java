package com.jgonzal.retail.adapters.out.persistence.mapper;

import org.mapstruct.Mapper;

import com.jgonzal.retail.adapters.out.persistence.entity.CustomerEntity;
import com.jgonzal.retail.model.Customer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    CustomerEntity toEntity(Customer customer);
    
    Customer toDomain(CustomerEntity customerEntity);
    
    List<Customer> toDomainList(List<CustomerEntity> customerEntities);
} 