package com.concessionaria.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
	boolean existsByName(String name);
}
