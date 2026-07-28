package com.concessionaria.backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.entity.enums.CustomerType;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	boolean existsByNameOrDocumentOrEmailOrPhone(String name, String document, String email, String phone);
	
	@Query("""
			SELECT c
			FROM Customer c
			WHERE (UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%')))
			AND (:customerType IS NULL OR c.customerType = :customerType)
			""")
	Page<Customer> listCustomersFilteredByNameAndCustomerType(@Param("name") String name, @Param("customerType") CustomerType customerType, Pageable pageable);
}