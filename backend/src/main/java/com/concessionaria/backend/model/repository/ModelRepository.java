package com.concessionaria.backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, String>  {
	boolean existsByName(String name);

	Page<Model> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
