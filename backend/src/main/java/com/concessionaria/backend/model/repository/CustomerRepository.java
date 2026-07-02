package com.concessionaria.backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	boolean existsByNameOrDocumentOrEmailOrPhone(String name, String document, String email, String phone);

	@Query("""
			SELECT c
			FROM Customer c
			WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
			""")
	Page<Customer> listCustomers(@Param("name") String name, Pageable pageable);
}