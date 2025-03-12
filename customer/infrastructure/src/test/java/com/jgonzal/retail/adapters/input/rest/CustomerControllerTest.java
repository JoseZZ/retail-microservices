package com.jgonzal.retail.adapters.input.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgonzal.retail.adapters.input.rest.data.request.CustomerRequest;
import com.jgonzal.retail.adapters.input.rest.data.response.CustomerResponse;
import com.jgonzal.retail.adapters.input.rest.mapper.CustomerRestMapper;
import com.jgonzal.retail.exception.CustomerNotFoundException;
import com.jgonzal.retail.model.Customer;
import com.jgonzal.retail.ports.input.CustomerService;
import com.jgonzal.retail.adapters.input.rest.exception.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRestMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(customerController).setControllerAdvice(new GlobalExceptionHandler()).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        // Given
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");

        Customer customer = Customer.builder().build();
        Customer createdCustomer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("John Doe");
        expectedResponse.setEmail("john.doe@example.com");

        when(customerMapper.toDomain(request)).thenReturn(customer);
        when(customerService.createCustomer(customer)).thenReturn(createdCustomer);
        when(customerMapper.toResponse(createdCustomer)).thenReturn(expectedResponse);

        // When/Then
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerMapper).toDomain(request);
        verify(customerService).createCustomer(customer);
        verify(customerMapper).toResponse(createdCustomer);
    }

    @Test
    void getCustomerById_ShouldReturnCustomer() throws Exception {
        // Given
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setId(customerId);
        expectedResponse.setName("John Doe");
        expectedResponse.setEmail("john.doe@example.com");

        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(expectedResponse);

        // When/Then
        mockMvc.perform(get("/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService).getCustomerById(customerId);
        verify(customerMapper).toResponse(customer);
    }

    @Test
    void getCustomerById_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Given
        Long customerId = 999L;
        when(customerService.getCustomerById(customerId)).thenThrow(new CustomerNotFoundException(customerId));

        // When/Then
        mockMvc.perform(get("/customers/{id}", customerId))
                .andExpect(status().isNotFound());

        verify(customerService).getCustomerById(customerId);
        verify(customerMapper, never()).toResponse(any());
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() throws Exception {
        // Given
        List<Customer> customers = List.of(
                Customer.builder().id(1L).name("John Doe").email("john@example.com").build(),
                Customer.builder().id(2L).name("Jane Doe").email("jane@example.com").build());
        List<CustomerResponse> expectedResponses = List.of(
                new CustomerResponse(1L, "John Doe", "john@example.com"),
                new CustomerResponse(2L, "Jane Doe", "jane@example.com"));

        when(customerService.getAllCustomers()).thenReturn(customers);
        when(customerMapper.toResponseList(customers)).thenReturn(expectedResponses);

        // When/Then
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));

        verify(customerService).getAllCustomers();
        verify(customerMapper).toResponseList(customers);
    }

    @Test
    void deleteCustomer_ShouldReturnNoContent_WhenCustomerExists() throws Exception {
        // Given
        Long customerId = 1L;
        doNothing().when(customerService).deleteCustomer(customerId);

        // When/Then
        mockMvc.perform(delete("/customers/{id}", customerId))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(customerId);
    }

    @Test
    void deleteCustomer_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Given
        Long customerId = 999L;
        doThrow(new CustomerNotFoundException(customerId)).when(customerService).deleteCustomer(customerId);

        // When/Then
        mockMvc.perform(delete("/customers/{id}", customerId))
                .andExpect(status().isNotFound());

        verify(customerService).deleteCustomer(customerId);
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomer_WhenCustomerExists() throws Exception {
        // Given
        Long customerId = 1L;
        CustomerRequest request = new CustomerRequest();
        request.setName("John Updated");
        request.setEmail("john.updated@example.com");

        Customer customerToUpdate = Customer.builder()
                .name("John Updated")
                .email("john.updated@example.com")
                .build();

        Customer updatedCustomer = Customer.builder()
                .id(customerId)
                .name("John Updated")
                .email("john.updated@example.com")
                .build();

        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setId(customerId);
        expectedResponse.setName("John Updated");
        expectedResponse.setEmail("john.updated@example.com");

        when(customerMapper.toDomain(request)).thenReturn(customerToUpdate);
        when(customerService.updateCustomer(customerId, customerToUpdate)).thenReturn(updatedCustomer);
        when(customerMapper.toResponse(updatedCustomer)).thenReturn(expectedResponse);

        // When/Then
        mockMvc.perform(put("/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));

        verify(customerMapper).toDomain(request);
        verify(customerService).updateCustomer(customerId, customerToUpdate);
        verify(customerMapper).toResponse(updatedCustomer);
    }

    @Test
    void updateCustomer_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Given
        Long customerId = 999L;
        CustomerRequest request = new CustomerRequest();
        request.setName("John Updated");
        request.setEmail("john.updated@example.com");

        Customer customerToUpdate = Customer.builder()
                .name("John Updated")
                .email("john.updated@example.com")
                .build();

        when(customerMapper.toDomain(request)).thenReturn(customerToUpdate);
        when(customerService.updateCustomer(customerId, customerToUpdate))
                .thenThrow(new CustomerNotFoundException(customerId));

        // When/Then
        mockMvc.perform(put("/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(customerMapper).toDomain(request);
        verify(customerService).updateCustomer(customerId, customerToUpdate);
        verify(customerMapper, never()).toResponse(any());
    }
}