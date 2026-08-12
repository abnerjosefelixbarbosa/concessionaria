package com.concessionaria.backend.model.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.entity.enums.TransmissionType;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
	boolean existsByPlate(String plate);
	
	@Query("""
	SELECT v
	FROM Vehicle v
	WHERE (v.transmissionType = :transmissionType OR :transmissionType IS NULL)
	AND ((v.price >= 0 AND v.price <= :price) OR :price IS NULL) 		
	""")
	Page<Vehicle> listVehicleByTransmissionTypeAndPrice(@Param("transmissionType") TransmissionType transmissionType, @Param("price") BigDecimal price, Pageable pageable);
}