package com.concessionaria.backend.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModelRequestDTO(
		@NotNull(message = "Nome deve ser obrigatório.")
		@NotEmpty(message = "Nome deve ser obrigatório.")
		@Size(message = "Nome deve ter até 30 caracteres.", max = 30)
	    String name,
	    @NotNull(message = "Nome da marca deve ser obrigatório.")
		@NotEmpty(message = "Nome da marca deve ser obrigatório.")
		String brandName
) {}
