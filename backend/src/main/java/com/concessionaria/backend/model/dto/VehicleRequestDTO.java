package com.concessionaria.backend.model.dto;

import java.math.BigDecimal;

import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VehicleRequestDTO(
		@NotNull(message = "Placa deve ser obrigatório.")
		@NotEmpty(message = "Placa deve ser obrigatório.")
		@Size(message = "Placa deve ter até 10 caracteres.", max = 10)
		String plate,
		@NotNull(message = "Tipo de transmissão deve ser obrigatório.")
		TransmissionType transmissionType,
		@NotNull(message = "Status de veiculo deve ser obrigatório.")
		VehicleStatus vehicleStatus,
		@NotNull(message = "Cor deve ser obrigatório.")
		@NotEmpty(message = "Cor deve ser obrigatório.")
		@Size(max = 30, message = "Cor deve ter até 30 caracteres.")
		String color,
		@NotNull(message = "Preço deve ser obrigatório.")
		BigDecimal price,
		@NotNull(message = "Nome do modelo deve ser obrigatório.")
		@NotEmpty(message = "Nome do modelo deve ser obrigatório.")
		@Size(message = "Nome do modelo deve ter até 30 caracteres.", max = 30)
		String modelName
) {}