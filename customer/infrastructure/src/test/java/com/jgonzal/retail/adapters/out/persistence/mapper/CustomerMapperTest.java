package com.jgonzal.retail.adapters.out.persistence.mapper;

import com.jgonzal.retail.adapters.out.persistence.entity.CustomerEntity;
import com.jgonzal.retail.model.Customer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void shouldMapCustomerToEntity() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        // When
        CustomerEntity entity = mapper.toEntity(customer);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(customer.getId());
        assertThat(entity.getName()).isEqualTo(customer.getName());
        assertThat(entity.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void shouldMapEntityToCustomer() {
        // Given
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1L);
        entity.setName("John Doe");
        entity.setEmail("john.doe@example.com");

        // When
        Customer customer = mapper.toDomain(entity);

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(entity.getId());
        assertThat(customer.getName()).isEqualTo(entity.getName());
        assertThat(customer.getEmail()).isEqualTo(entity.getEmail());
    }

    @Test
    void shouldHandleNullValues() {
        // Given
        Customer customer = null;
        CustomerEntity entity = null;

        // When
        CustomerEntity mappedEntity = mapper.toEntity(customer);
        Customer mappedCustomer = mapper.toDomain(entity);

        // Then
        assertThat(mappedEntity).isNull();
        assertThat(mappedCustomer).isNull();
    }
} 