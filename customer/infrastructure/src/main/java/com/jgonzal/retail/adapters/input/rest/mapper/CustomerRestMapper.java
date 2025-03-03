package com.jgonzal.retail.adapters.input.rest.mapper;

import com.jgonzal.retail.adapters.input.rest.data.request.CustomerRequest;
import com.jgonzal.retail.adapters.input.rest.data.response.CustomerResponse;
import com.jgonzal.retail.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {
    
    Customer toDomain(CustomerRequest customerRequest);
    
    CustomerResponse toResponse(Customer customer);
    
    List<CustomerResponse> toResponseList(List<Customer> customers);
} 