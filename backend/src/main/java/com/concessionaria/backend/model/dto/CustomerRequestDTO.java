package com.concessionaria.backend.model.dto;

import com.concessionaria.backend.model.entity.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de requisição do cliente.")
public record CustomerRequestDTO(
		@Schema(description = "Nome do cliente.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Nome deve ser obrigatório.")
		@NotEmpty(message = "Nome deve ser obrigatório.")
		@Size(max = 100, message = "Nome deve ter até 100 caracteres.")
		String name,
		@Schema(description = "Documento CPF ou CNPJ do cliente.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Documento deve ser obrigatório.")
		@NotEmpty(message = "Documento deve ser obrigatório.")
		String document,
		@Schema(description = "Email do cliente.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Email deve ser obrigatório.")
		@NotEmpty(message = "Email deve ser obrigatório.")
		@Email(message = "Email deve ser valido.")
		String email,
		@Schema(description = "Telefone do cliente.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Telefone deve ser obrigatório.")
		@NotEmpty(message = "Telefone deve ser obrigatório.")
		@Size(max = 30, message = "Telefone deve ter até 30 caracteres.")
		String phone,
		@Schema(description = "Tipo de cliente.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Tipo de cliente deve ser obrigatório.")
		CustomerType customerType
) {}