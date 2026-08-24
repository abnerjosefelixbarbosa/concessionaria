package com.concessionaria.backend.model.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
	boolean existsByPlate(String plate);

	@Query("""
			SELECT v
			FROM Vehicle v
			WHERE (v.transmissionType = :transmissionType OR :transmissionType IS NULL)
			AND ((v.price <= :price) OR :price IS NULL)
			AND (v.vehicleStatus = :vehicleStatus OR :vehicleStatus IS NULL)
			AND (UPPER(v.color) LIKE UPPER(CONCAT('%', :color, '%')))
			AND (UPPER(v.plate) LIKE UPPER(CONCAT('%', :plate, '%')))
			""")
	Page<Vehicle> listVehicles(@Param("transmissionType") TransmissionType transmissionType,
			@Param("price") BigDecimal price, @Param("vehicleStatus") VehicleStatus vehicleStatus,
			@Param("color") String color, @Param("plate") String plate, Pageable pageable);

	Optional<Vehicle> findByPlate(String plate);
}