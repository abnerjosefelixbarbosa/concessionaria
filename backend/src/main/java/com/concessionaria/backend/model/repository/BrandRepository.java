package com.concessionaria.backend.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
	boolean existsByName(String name);
	
	Page<Brand> findAllByNameContainsIgnoreCase(String name, Pageable pageable);

	Optional<Brand> findByNameIgnoreCase(String name);
}
