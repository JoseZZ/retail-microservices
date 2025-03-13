package com.jgonzal.retail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;    
import com.jgonzal.retail.exception.CustomerValidationException;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    
    private Long id;
    private String name;
    private String email;
    private String dni;
    private Integer age;

    public void validate() {
        validateDni();
        validateAge();
    }

    private void validateDni() {
        if (dni == null || dni.length() != 9) {
            throw new CustomerValidationException("El DNI debe tener una longitud de 9 caracteres");
        }

        String numbers = dni.substring(0, 8);
        String letter = dni.substring(8, 9);

        if (!numbers.matches("\\d{8}")) {
            throw new CustomerValidationException("Los primeros 8 caracteres del DNI deben ser números");
        }

        if (!letter.matches("[A-Za-z]")) {
            throw new CustomerValidationException("El último carácter del DNI debe ser una letra");
        }
    }

    private void validateAge() {
        if (age == null) {
            throw new CustomerValidationException("La edad no puede ser nula");
        }
        if (age < 18) {
            throw new CustomerValidationException("El cliente debe ser mayor de edad (18 años o más)");
        }
    }
}
