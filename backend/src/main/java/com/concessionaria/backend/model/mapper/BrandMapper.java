package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;
import com.concessionaria.backend.model.entity.Brand;

public class BrandMapper {
	public static Brand toBrand(BrandRequestDTO dto) {
		return new Brand(null, dto.name(), null);
	}

	public static BrandResponseDTO toBrandResponseDTO(Brand brand) {
		return new BrandResponseDTO(brand.getId(), brand.getName());
	}
}
