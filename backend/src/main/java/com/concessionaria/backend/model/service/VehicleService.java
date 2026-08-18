package com.concessionaria.backend.model.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.dto.VehicleResponseDTO;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;

public interface VehicleService {
	VehicleResponseDTO registerVehicle(VehicleRequestDTO dto);
	
	VehicleResponseDTO updateVehicleById(String id, VehicleRequestDTO dto);
	
	VehicleResponseDTO findVehicleById(String id);

	Page<VehicleResponseDTO> listVehicles(TransmissionType transmissionType, BigDecimal price, VehicleStatus vehicleStatus,
			 String color, String plate, Pageable pageable);
}