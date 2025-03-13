package com.jgonzal.retail.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void shouldCreateCustomerUsingBuilder() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String dni = "12345678A";
        Integer age = 30;

        // When
        Customer customer = Customer.builder()
                .id(id)
                .name(name)
                .email(email)
                .dni(dni)
                .age(age)
                .build();

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getDni()).isEqualTo(dni);
        assertThat(customer.getAge()).isEqualTo(age);
    }

    @Test
    void shouldCreateCustomerUsingAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String dni = "12345678A";
        Integer age = 30;

        // When
        Customer customer = new Customer(id, name, email, dni, age);

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getDni()).isEqualTo(dni);
        assertThat(customer.getAge()).isEqualTo(age);
    }

    @Test
    void shouldCreateEmptyCustomerUsingNoArgsConstructor() {
        // When
        Customer customer = new Customer();

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNull();
        assertThat(customer.getName()).isNull();
        assertThat(customer.getEmail()).isNull();
        assertThat(customer.getDni()).isNull();
        assertThat(customer.getAge()).isNull();
    }

    @Test
    void shouldSetAndGetAllProperties() {
        // Given
        Customer customer = new Customer();
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String dni = "12345678A";
        Integer age = 30;

        // When
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        customer.setDni(dni);
        customer.setAge(age);

        // Then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getDni()).isEqualTo(dni);
        assertThat(customer.getAge()).isEqualTo(age);
    }

    @Test
    void shouldAllowNullValues() {
        // Given
        Customer customer = Customer.builder()
                .id(null)
                .name(null)
                .email(null)
                .dni(null)
                .age(null)
                .build();

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNull();
        assertThat(customer.getName()).isNull();
        assertThat(customer.getEmail()).isNull();
        assertThat(customer.getDni()).isNull();
        assertThat(customer.getAge()).isNull();
    }
} 