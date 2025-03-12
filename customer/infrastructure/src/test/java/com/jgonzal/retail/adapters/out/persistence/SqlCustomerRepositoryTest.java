package com.jgonzal.retail.adapters.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jgonzal.retail.adapters.out.persistence.entity.CustomerEntity;
import com.jgonzal.retail.adapters.out.persistence.mapper.CustomerMapper;
import com.jgonzal.retail.adapters.out.persistence.repository.JpaCustomerRepository;
import com.jgonzal.retail.model.Customer;

@ExtendWith(MockitoExtension.class)
class SqlCustomerRepositoryTest {

    @Mock
    private JpaCustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private SqlCustomerRepository sqlCustomerRepository;

    @Test
    void save_ShouldSaveAndReturnCustomer() {
        // Given
        Customer customer = Customer.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        CustomerEntity entity = new CustomerEntity();
        entity.setName("John Doe");
        entity.setEmail("john.doe@example.com");

        when(mapper.toEntity(customer)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(customer);

        // When
        Customer result = sqlCustomerRepository.save(customer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getEmail()).isEqualTo(customer.getEmail());
        verify(repository).save(entity);
        verify(mapper).toEntity(customer);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findById_ShouldReturnCustomer_WhenExists() {
        // Given
        Long id = 1L;
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName("John Doe");
        Customer customer = Customer.builder()
                .id(id)
                .name("John Doe")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(customer);

        // When
        Optional<Customer> result = sqlCustomerRepository.findById(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getName()).isEqualTo("John Doe");
        verify(repository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Customer> result = sqlCustomerRepository.findById(id);

        // Then
        assertThat(result).isEmpty();
        verify(repository).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnAllCustomers() {
        // Given
        List<CustomerEntity> entities = List.of(
            new CustomerEntity(),
            new CustomerEntity()
        );
        List<Customer> customers = List.of(
            Customer.builder().build(),
            Customer.builder().build()
        );

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDomainList(entities)).thenReturn(customers);

        // When
        List<Customer> result = sqlCustomerRepository.findAll();

        // Then
        assertThat(result).hasSize(2);
        verify(repository).findAll();
        verify(mapper, times(1)).toDomainList(any());
    }

    @Test
    void deleteById_ShouldReturnTrue_WhenCustomerExists() {
        // Given
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        // When
        boolean result = sqlCustomerRepository.deleteById(id);

        // Then
        assertThat(result).isTrue();
        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void deleteById_ShouldReturnFalse_WhenCustomerDoesNotExist() {
        // Given
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        // When
        boolean result = sqlCustomerRepository.deleteById(id);

        // Then
        assertThat(result).isFalse();
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any());
    }

    @Test
    void update_ShouldReturnUpdatedCustomer_WhenCustomerExists() {
        // Given
        Long id = 1L;
        Customer customer = Customer.builder()
                .id(id)
                .name("John Updated")
                .email("john.updated@example.com")
                .build();
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName("John Updated");
        entity.setEmail("john.updated@example.com");

        when(repository.existsById(id)).thenReturn(true);
        when(mapper.toEntity(customer)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(customer);

        // When
        Optional<Customer> result = sqlCustomerRepository.update(customer);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getName()).isEqualTo("John Updated");
        assertThat(result.get().getEmail()).isEqualTo("john.updated@example.com");
        verify(repository).existsById(id);
        verify(mapper).toEntity(customer);
        verify(repository).save(entity);
        verify(mapper).toDomain(entity);
    }

    @Test
    void update_ShouldReturnEmpty_WhenCustomerDoesNotExist() {
        // Given
        Long id = 1L;
        Customer customer = Customer.builder()
                .id(id)
                .name("John Updated")
                .email("john.updated@example.com")
                .build();

        when(repository.existsById(id)).thenReturn(false);

        // When
        Optional<Customer> result = sqlCustomerRepository.update(customer);

        // Then
        assertThat(result).isEmpty();
        verify(repository).existsById(id);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDomain(any());
    }
} 