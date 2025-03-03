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

        // When
        Customer customer = Customer.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldCreateCustomerUsingAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";

        // When
        Customer customer = new Customer(id, name, email);

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
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
    }

    @Test
    void shouldSetAndGetAllProperties() {
        // Given
        Customer customer = new Customer();
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";

        // When
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);

        // Then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldAllowNullValues() {
        // Given
        Customer customer = Customer.builder()
                .id(null)
                .name(null)
                .email(null)
                .build();

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNull();
        assertThat(customer.getName()).isNull();
        assertThat(customer.getEmail()).isNull();
    }
} 