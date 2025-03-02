package com.jgonzal.retail.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jgonzal.retail.adapters.input.rest.data.request.CustomerRequest;
import com.jgonzal.retail.adapters.input.rest.data.response.CustomerResponse;
import com.jgonzal.retail.adapters.input.rest.mapper.CustomerRestMapper;
import com.jgonzal.retail.ports.input.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRestMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerRestMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        var customer = customerMapper.toDomain(customerRequest);
        var createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(customerMapper.toResponse(createdCustomer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        var customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerMapper.toResponse(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        var customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customerMapper.toResponseList(customers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
