package com.concessionaria.backend.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, String>  {
	boolean existsByName(String name);
	
	@Query("""
	SELECT m
	FROM Model m
	WHERE (UPPER(m.name) LIKE UPPER(CONCAT('%', :name, '%')))			
	""")
	Page<Model> listModelsByName(String name, Pageable pageable);
	
	Optional<Model> findByName(String name);
}
