package com.concessionaria.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de requesição da marca.")
public record BrandRequestDTO(
		@Schema(description = "Nome da marca.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Nome deve ser obrigatório.")
		@NotEmpty(message = "Nome deve ser obrigatório.")
		@Size(max = 30, message = "Nome deve ter até 30 caracteres.")
		String name
) {}