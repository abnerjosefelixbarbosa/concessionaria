package com.concessionaria.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta do modelo.")
public record ModelResponseDTO(
		@Schema(description = "Id do modelo.")
		String id,
		@Schema(description = "Nome do modelo.")
		String name,
		@Schema(description = "Nome da marca.")
		String brandName
) {}