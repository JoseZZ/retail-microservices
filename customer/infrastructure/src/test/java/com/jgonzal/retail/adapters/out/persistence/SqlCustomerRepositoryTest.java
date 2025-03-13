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
                .dni("12345678A")
                .age(30)
                .build();
        CustomerEntity entity = new CustomerEntity();
        entity.setName("John Doe");
        entity.setEmail("john.doe@example.com");
        entity.setDni("12345678A");
        entity.setAge(30);

        when(mapper.toEntity(customer)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(customer);

        // When
        Customer result = sqlCustomerRepository.save(customer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getEmail()).isEqualTo(customer.getEmail());
        assertThat(result.getDni()).isEqualTo(customer.getDni());
        assertThat(result.getAge()).isEqualTo(customer.getAge());
        verify(repository).save(entity);
        verify(mapper).toEntity(customer);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findById_ShouldReturnCustomer_WhenCustomerExists() {
        // Given
        Long id = 1L;
        Customer customer = Customer.builder()
                .id(id)
                .name("John Doe")
                .email("john.doe@example.com")
                .dni("12345678A")
                .age(30)
                .build();
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName("John Doe");
        entity.setEmail("john.doe@example.com");
        entity.setDni("12345678A");
        entity.setAge(30);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(customer);

        // When
        Optional<Customer> result = sqlCustomerRepository.findById(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getName()).isEqualTo("John Doe");
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get().getDni()).isEqualTo("12345678A");
        assertThat(result.get().getAge()).isEqualTo(30);
        verify(repository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCustomerDoesNotExist() {
        // Given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Customer> result = sqlCustomerRepository.findById(id);

        // Then
        assertThat(result).isEmpty();
        verify(repository).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnListOfCustomers() {
        // Given
        List<Customer> customers = List.of(
                Customer.builder().id(1L).name("John Doe").email("john@example.com").dni("12345678A").age(30).build(),
                Customer.builder().id(2L).name("Jane Doe").email("jane@example.com").dni("87654321B").age(25).build());
        List<CustomerEntity> entities = List.of(
                createCustomerEntity(1L, "John Doe", "john@example.com", "12345678A", 30),
                createCustomerEntity(2L, "Jane Doe", "jane@example.com", "87654321B", 25));

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDomainList(entities)).thenReturn(customers);

        // When
        List<Customer> result = sqlCustomerRepository.findAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(customers);
        verify(repository).findAll();
        verify(mapper).toDomainList(entities);
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
        Long id = 999L;
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
                .dni("12345678A")
                .age(35)
                .build();
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName("John Updated");
        entity.setEmail("john.updated@example.com");
        entity.setDni("12345678A");
        entity.setAge(35);

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
        assertThat(result.get().getDni()).isEqualTo("12345678A");
        assertThat(result.get().getAge()).isEqualTo(35);
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
                .dni("12345678A")
                .age(35)
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

    private CustomerEntity createCustomerEntity(Long id, String name, String email, String dni, Integer age) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setEmail(email);
        entity.setDni(dni);
        entity.setAge(age);
        return entity;
    }
} 