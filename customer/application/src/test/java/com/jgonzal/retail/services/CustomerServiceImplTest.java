package com.jgonzal.retail.services;

import com.jgonzal.retail.model.Customer;
import com.jgonzal.retail.ports.output.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void createCustomer_ShouldSaveAndReturnCustomer() {
        // Given
        Customer customer = Customer.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer result = customerService.createCustomer(customer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getEmail()).isEqualTo(customer.getEmail());
        verify(customerRepository).save(customer);
    }

    @Test
    void getCustomerById_ShouldReturnCustomer() {
        // Given
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        when(customerRepository.findById(customerId)).thenReturn(customer);

        // When
        Customer result = customerService.getCustomerById(customerId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(customerId);
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getEmail()).isEqualTo(customer.getEmail());
        verify(customerRepository).findById(customerId);
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // Given
        List<Customer> customers = List.of(
            Customer.builder().id(1L).name("John Doe").email("john@example.com").build(),
            Customer.builder().id(2L).name("Jane Doe").email("jane@example.com").build()
        );
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<Customer> result = customerService.getAllCustomers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(customers);
        verify(customerRepository).findAll();
    }

    @Test
    void deleteCustomer_ShouldCallRepositoryDelete() {
        // Given
        Long customerId = 1L;

        // When
        customerService.deleteCustomer(customerId);

        // Then
        verify(customerRepository).deleteById(customerId);
    }

    @Test
    void getAllCustomers_ShouldReturnEmptyList_WhenNoCustomersExist() {
        // Given
        when(customerRepository.findAll()).thenReturn(List.of());

        // When
        List<Customer> result = customerService.getAllCustomers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(customerRepository).findAll();
    }

    @Test
    void getCustomerById_ShouldReturnNull_WhenCustomerNotFound() {
        // Given
        Long customerId = 999L;
        when(customerRepository.findById(customerId)).thenReturn(null);

        // When
        Customer result = customerService.getCustomerById(customerId);

        // Then
        assertThat(result).isNull();
        verify(customerRepository).findById(customerId);
    }
} 