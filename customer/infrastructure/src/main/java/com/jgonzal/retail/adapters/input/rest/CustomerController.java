package com.jgonzal.retail.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jgonzal.retail.adapters.input.rest.data.request.CustomerRequest;
import com.jgonzal.retail.adapters.input.rest.data.response.CustomerResponse;
import com.jgonzal.retail.adapters.input.rest.mapper.CustomerRestMapper;
import com.jgonzal.retail.ports.input.CustomerService;
import org.springframework.http.HttpStatus;

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
        return new ResponseEntity<>(customerMapper.toResponse(createdCustomer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        var customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customerMapper.toResponse(customer), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        var customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customerMapper.toResponseList(customers), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        var customer = customerMapper.toDomain(customerRequest);
        var updatedCustomer = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(customerMapper.toResponse(updatedCustomer), HttpStatus.OK);
    }
}
