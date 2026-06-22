package com.conssecionaria.conssecionaria_backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conssecionaria.conssecionaria_backend.model.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {

}