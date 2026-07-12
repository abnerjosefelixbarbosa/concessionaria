package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.dto.ModelResponseDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;

public class ModelMapper {
	public static Model toModel(ModelRequestDTO dto) {
		Brand brand = new Brand(null, dto.brandName(), null);

		return new Model(null, dto.name(), brand, null);
	}

	public static ModelResponseDTO toModelResponseDTO(Model model) {
		return new ModelResponseDTO(model.getId(), model.getName(), model.getBrand().getName());
	}
}
