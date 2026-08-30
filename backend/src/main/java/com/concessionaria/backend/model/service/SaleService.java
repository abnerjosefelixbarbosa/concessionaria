package com.concessionaria.backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.SaleRequestDTO;
import com.concessionaria.backend.model.dto.SaleResponseDTO;
import com.concessionaria.backend.model.entity.enums.PaymentType;

public interface SaleService {
	SaleResponseDTO registerSale(SaleRequestDTO dto);

	void deleterSaleById(String id);

	Page<SaleResponseDTO> listSales(PaymentType paymentType, Pageable pageable);
}