package com.concessionaria.backend.model.dto;

import java.math.BigDecimal;

import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;

public record VehicleResponseDTO(
		String id,
		String plate,
		TransmissionType transmissionType,
		VehicleStatus vehicleStatus,
		String color,
		BigDecimal price,
		String modelName
) {}