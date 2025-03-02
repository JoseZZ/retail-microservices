package com.jgonzal.retail.customer.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jgonzal.retail.customer.infrastructure.adapters.out.persistence.entity.CustomerEntity;
@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
