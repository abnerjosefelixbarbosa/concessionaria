package com.concessionaria.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
