package com.jgonzal.retail.services;

import com.jgonzal.retail.model.Customer;
import com.jgonzal.retail.ports.output.CustomerRepository;
import com.jgonzal.retail.exception.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                .dni("12345678A")
                .age(30)
                .build();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer result = customerService.createCustomer(customer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getEmail()).isEqualTo(customer.getEmail());
        assertThat(result.getDni()).isEqualTo(customer.getDni());
        assertThat(result.getAge()).isEqualTo(customer.getAge());
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
                .dni("12345678A")
                .age(30)
                .build();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        Customer result = customerService.getCustomerById(customerId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(customerId);
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getEmail()).isEqualTo(customer.getEmail());
        assertThat(result.getDni()).isEqualTo(customer.getDni());
        assertThat(result.getAge()).isEqualTo(customer.getAge());
        verify(customerRepository).findById(customerId);
    }

    @Test
    void getCustomerById_ShouldThrowException_WhenCustomerNotFound() {
        // Given
        Long customerId = 999L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> customerService.getCustomerById(customerId))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Cliente no encontrado con id: " + customerId);
        verify(customerRepository).findById(customerId);
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // Given
        List<Customer> customers = List.of(
                Customer.builder().id(1L).name("John Doe").email("john@example.com").dni("12345678A").age(30).build(),
                Customer.builder().id(2L).name("Jane Doe").email("jane@example.com").dni("87654321B").age(25).build());
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
    void deleteCustomer_ShouldDeleteCustomer_WhenCustomerExists() {
        // Given
        Long customerId = 1L;
        when(customerRepository.deleteById(customerId)).thenReturn(true);

        // When
        customerService.deleteCustomer(customerId);

        // Then
        verify(customerRepository).deleteById(customerId);
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        // Given
        Long customerId = 123L;
        when(customerRepository.deleteById(customerId)).thenReturn(false);

        // When/Then
        assertThatThrownBy(() -> customerService.deleteCustomer(customerId))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Cliente no encontrado con id: " + customerId);
        verify(customerRepository).deleteById(customerId);
    }

    @Test
    void updateCustomer_ShouldUpdateAndReturnCustomer_WhenCustomerExists() {
        // Given
        Long customerId = 1L;
        Customer customerToUpdate = Customer.builder()
                .id(customerId)
                .name("John Updated")
                .email("john.updated@example.com")
                .dni("12345678A")
                .age(35)
                .build();
        Customer expectedCustomer = Customer.builder()
                .id(customerId)
                .name("John Updated")
                .email("john.updated@example.com")
                .dni("12345678A")
                .age(35)
                .build();

        when(customerRepository.update(customerToUpdate)).thenReturn(Optional.of(expectedCustomer));

        // When
        Customer result = customerService.updateCustomer(customerId, customerToUpdate);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(customerId);
        assertThat(result.getName()).isEqualTo(customerToUpdate.getName());
        assertThat(result.getEmail()).isEqualTo(customerToUpdate.getEmail());
        assertThat(result.getDni()).isEqualTo(customerToUpdate.getDni());
        assertThat(result.getAge()).isEqualTo(customerToUpdate.getAge());
        verify(customerRepository).update(customerToUpdate);
    }

    @Test
    void updateCustomer_ShouldThrowException_WhenCustomerNotFound() {
        // Given
        Long customerId = 999L;
        Customer customerToUpdate = Customer.builder()
                .id(customerId)
                .name("John Updated")
                .email("john.updated@example.com")
                .dni("12345678A")
                .age(35)
                .build();

        when(customerRepository.update(customerToUpdate)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> customerService.updateCustomer(customerId, customerToUpdate))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Cliente no encontrado con id: " + customerId);
        verify(customerRepository).update(customerToUpdate);
    }
}