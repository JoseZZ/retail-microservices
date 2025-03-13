package com.jgonzal.retail.adapters.out.persistence.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerEntityTest {

    @Test
    void shouldCreateCustomerEntity() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String dni = "12345678A";
        Integer age = 30;

        // When
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setEmail(email);
        entity.setDni(dni);
        entity.setAge(age);

        // Then
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getEmail()).isEqualTo(email);
        assertThat(entity.getDni()).isEqualTo(dni);
        assertThat(entity.getAge()).isEqualTo(age);
    }

    @Test
    void shouldCreateEmptyCustomerEntity() {
        // When
        CustomerEntity entity = new CustomerEntity();

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isNull();
        assertThat(entity.getEmail()).isNull();
        assertThat(entity.getDni()).isNull();
        assertThat(entity.getAge()).isNull();
    }
} 