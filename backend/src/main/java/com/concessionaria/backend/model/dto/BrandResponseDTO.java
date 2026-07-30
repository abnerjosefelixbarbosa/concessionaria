package com.concessionaria.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de respota da marca.")
public record BrandResponseDTO(
		@Schema(description = "Id da marca.", requiredMode = Schema.RequiredMode.REQUIRED)
		String id,
		@Schema(description = "Nome da marca.", requiredMode = Schema.RequiredMode.REQUIRED)
		String name
) {}