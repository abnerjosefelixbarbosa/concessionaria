package com.concessionaria.backend.model.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.concessionaria.backend.model.dto.SaleRequestDTO;
import com.concessionaria.backend.model.dto.SaleResponseDTO;
import com.concessionaria.backend.model.entity.Sale;

public class SaleMapper {
	public static Sale toSale(SaleRequestDTO dto) {
		Sale sale = new Sale(null, LocalDate.now(), dto.paymentType(), BigDecimal.ZERO, null, null, null);

		return sale;
	}

	public static SaleResponseDTO toSaleResponseDTO(Sale sale) {
		SaleResponseDTO dto = new SaleResponseDTO(sale.getId(), sale.getSaleDate(), sale.getPaymentType(),
				sale.getTotalValue(), sale.getEmployee().getName(), sale.getCustomer().getName());

		return dto;
	}
}