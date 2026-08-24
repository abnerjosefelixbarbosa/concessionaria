package com.concessionaria.backend.model.dto;

import java.util.List;

import com.concessionaria.backend.model.entity.enums.PaymentType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SaleRequestDTO(
		@NotNull(message = "Tipo do pagamento deve ser obrigatório.")
		PaymentType paymentType,
		@NotNull(message = "Matricula do funcionário deve ser obrigatório.")
		@NotEmpty(message = "Matricula do funcionário deve ser obrigatório.")
		String matriculation,
		@NotNull(message = "Documento do cliente deve ser obrigatório.")
		@NotEmpty(message = "Documento do cliente deve ser obrigatório.")
		String document,
		@NotNull(message = "Items deve ser obrigatorios.")
		List<ItemRequestDTO> items
) {}