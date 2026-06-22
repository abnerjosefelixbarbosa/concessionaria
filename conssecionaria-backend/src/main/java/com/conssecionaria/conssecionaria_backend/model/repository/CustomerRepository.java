package com.conssecionaria.conssecionaria_backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conssecionaria.conssecionaria_backend.model.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}