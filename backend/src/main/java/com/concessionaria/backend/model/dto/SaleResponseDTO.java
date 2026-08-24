package com.concessionaria.backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.concessionaria.backend.model.entity.enums.PaymentType;

public record SaleResponseDTO(
		String id,
		LocalDate saleDate,
		PaymentType paymentType,
		BigDecimal totalValue,
		String employeeName,
		String customerName
) {}