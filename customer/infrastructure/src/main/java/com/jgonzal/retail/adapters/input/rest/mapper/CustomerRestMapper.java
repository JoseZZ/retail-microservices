package com.jgonzal.retail.adapters.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jgonzal.retail.adapters.input.rest.data.request.CustomerRequest;
import com.jgonzal.retail.adapters.input.rest.data.response.CustomerResponse;
import com.jgonzal.retail.model.Customer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {
    
    Customer toDomain(CustomerRequest customerRequest);
    
    @Mapping(target = "id", ignore = true)
    CustomerResponse toResponse(Customer customer);
    
    List<CustomerResponse> toResponseList(List<Customer> customers);
} 