package com.concessionaria.backend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(
		@NotNull(message = "Nome deve ser obrigatório.")
		@NotEmpty(message = "Nome deve ser obrigatório.")
		@Size(max = 100, message = "Nome deve ter até 100 caracteres.")
		String name,
		@NotNull(message = "Documento deve ser obrigatório.")
		@NotEmpty(message = "Documento deve ser obrigatório.")
		String document,
		@NotNull(message = "Email deve ser obrigatório.")
		@Email(message = "Email deve ser valido.")
		String email,
		@NotNull(message = "Telefone deve ser obrigatório.")
		@Size(max = 30, message = "Telefone deve ter até 30 caracteres.")
		String phone
) {

}
