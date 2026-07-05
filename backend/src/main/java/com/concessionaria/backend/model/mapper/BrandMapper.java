package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;
import com.concessionaria.backend.model.entity.Brand;

public class BrandMapper {
	public static Brand toBrand(BrandRequestDTO dto) {
		Brand brand = new Brand(null, dto.name(), null);

		return brand;
	}

	public static BrandResponseDTO toBrandResponseDTO(Brand brand) {
		BrandResponseDTO dto = new BrandResponseDTO(brand.getId(), brand.getName());

		return dto;
	}
}
