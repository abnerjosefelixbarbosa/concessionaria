package com.concessionaria.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta do cliente.")
public record CustomerResponseDTO(
		@Schema(description = "Id do cliente.")
		String id,
		@Schema(description = "Nome do cliente.")
		String name,
		@Schema(description = "Documento do cliente.")
		String document,
		@Schema(description = "Email do cliente.")
		String email,
		@Schema(description = "Telefone do cliente.")
		String phone
) {

}
