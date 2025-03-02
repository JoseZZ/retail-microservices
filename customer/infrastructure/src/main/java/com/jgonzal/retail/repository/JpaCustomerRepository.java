package com.jgonzal.retail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jgonzal.retail.adapters.out.persistence.entity.CustomerEntity;
@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
