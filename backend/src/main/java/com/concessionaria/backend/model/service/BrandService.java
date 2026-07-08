package com.concessionaria.backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;

public interface BrandService {
	BrandResponseDTO registerBrand(BrandRequestDTO dto);
	
	BrandResponseDTO updateBrandById(String id, BrandRequestDTO dto);

	BrandResponseDTO findBrandById(String id);

	Page<BrandResponseDTO> listBrandsFilteredByName(String name, Pageable pageable);
}
