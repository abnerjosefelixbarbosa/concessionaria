package com.concessionaria.backend.model.service;

import com.concessionaria.backend.model.dto.SaleRequestDTO;
import com.concessionaria.backend.model.dto.SaleResponseDTO;

public interface SaleService {

	SaleResponseDTO registerSale(SaleRequestDTO dto);

	void deleterSaleById(String id);

}