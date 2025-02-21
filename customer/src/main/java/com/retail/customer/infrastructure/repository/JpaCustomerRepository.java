package com.retail.customer.infrastructure.repository;

import com.retail.customer.domain.model.Customer;
import com.retail.customer.domain.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long>, CustomerRepository {
}
