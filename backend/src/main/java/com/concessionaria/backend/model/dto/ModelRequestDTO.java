package com.concessionaria.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de requisição do modelo.")
public record ModelRequestDTO(
		@Schema(description = "Nome do modelo.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Nome deve ser obrigatório.")
		@NotEmpty(message = "Nome deve ser obrigatório.")
		@Size(message = "Nome deve ter até 30 caracteres.", max = 30)
	    String name,
	    @Schema(description = "Nome da marca.", requiredMode = Schema.RequiredMode.REQUIRED)
	    @NotNull(message = "Nome da marca deve ser obrigatório.")
		@NotEmpty(message = "Nome da marca deve ser obrigatório.")
		String brandName
) {}