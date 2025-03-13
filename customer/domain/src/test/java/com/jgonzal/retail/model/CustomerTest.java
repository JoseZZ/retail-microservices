package com.jgonzal.retail.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.jgonzal.retail.exception.CustomerValidationException;

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

    @Test
    void shouldValidateValidCustomer() {
        // Given
        Customer customer = Customer.builder()
                .dni("12345678A")
                .age(30)
                .build();

        // When/Then
        assertThat(customer.getDni()).isEqualTo("12345678A");
        assertThat(customer.getAge()).isEqualTo(30);
        customer.validate(); // No debe lanzar excepción
    }

    @Test
    void shouldThrowException_WhenDniIsNull() {
        // Given
        Customer customer = Customer.builder()
                .dni(null)
                .age(30)
                .build();

        // When/Then
        assertThatThrownBy(() -> customer.validate())
                .isInstanceOf(CustomerValidationException.class)
                .hasMessage("El DNI debe tener una longitud de 9 caracteres");
    }

    @Test
    void shouldThrowException_WhenDniLengthIsNot9() {
        // Given
        Customer customer = Customer.builder()
                .dni("12345678")
                .age(30)
                .build();

        // When/Then
        assertThatThrownBy(() -> customer.validate())
                .isInstanceOf(CustomerValidationException.class)
                .hasMessage("El DNI debe tener una longitud de 9 caracteres");
    }

    @Test
    void shouldThrowException_WhenDniFirst8CharsAreNotNumbers() {
        // Given
        Customer customer = Customer.builder()
                .dni("1234567AB")
                .age(30)
                .build();

        // When/Then
        assertThatThrownBy(() -> customer.validate())
                .isInstanceOf(CustomerValidationException.class)
                .hasMessage("Los primeros 8 caracteres del DNI deben ser números");
    }

    @Test
    void shouldThrowException_WhenDniLastCharIsNotLetter() {
        // Given
        Customer customer = Customer.builder()
                .dni("123456789")
                .age(30)
                .build();

        // When/Then
        assertThatThrownBy(() -> customer.validate())
                .isInstanceOf(CustomerValidationException.class)
                .hasMessage("El último carácter del DNI debe ser una letra");
    }

    @Test
    void shouldThrowException_WhenAgeIsNull() {
        // Given
        Customer customer = Customer.builder()
                .dni("12345678A")
                .age(null)
                .build();

        // When/Then
        assertThatThrownBy(() -> customer.validate())
                .isInstanceOf(CustomerValidationException.class)
                .hasMessage("La edad no puede ser nula");
    }

    @Test
    void shouldThrowException_WhenAgeIsLessThan18() {
        // Given
        Customer customer = Customer.builder()
                .dni("12345678A")
                .age(17)
                .build();

        // When/Then
        assertThatThrownBy(() -> customer.validate())
                .isInstanceOf(CustomerValidationException.class)
                .hasMessage("El cliente debe ser mayor de edad (18 años o más)");
    }

    @Test
    void shouldValidateCustomerWithAge18() {
        // Given
        Customer customer = Customer.builder()
                .dni("12345678A")
                .age(18)
                .build();

        // When/Then
        assertThat(customer.getDni()).isEqualTo("12345678A");
        assertThat(customer.getAge()).isEqualTo(18);
        customer.validate(); // No debe lanzar excepción
    }
} 