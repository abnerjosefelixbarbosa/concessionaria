package com.concessionaria.backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;
import com.concessionaria.backend.model.entity.Brand;

public interface BrandService {
	BrandResponseDTO registerBrand(BrandRequestDTO dto);
	
	BrandResponseDTO updateBrandById(String id, BrandRequestDTO dto);

	BrandResponseDTO findBrandById(String id);
	
	Brand findByName(String name);

	Page<BrandResponseDTO> listBrands(String name, Pageable pageable);
}
