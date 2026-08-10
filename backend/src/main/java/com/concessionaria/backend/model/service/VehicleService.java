package com.concessionaria.backend.model.service;

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.dto.VehicleResponseDTO;

public interface VehicleService {
	VehicleResponseDTO registerVehicle(VehicleRequestDTO dto);
	
	VehicleResponseDTO updateVehicleById(String id, VehicleRequestDTO dto);
}