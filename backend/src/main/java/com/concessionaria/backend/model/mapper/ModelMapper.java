package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.dto.ModelResponseDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;

public class ModelMapper {
	public static Model toModel(ModelRequestDTO dto) {
		Brand brand = new Brand(null, dto.brandName(), null);

		Model model = new Model(null, dto.name(), brand, null);

		return model;
	}

	public static ModelResponseDTO toModelResponseDTO(Model model) {
		ModelResponseDTO modelResponseDTO = new ModelResponseDTO(model.getId(), model.getName(),
				model.getBrand().getName());

		return modelResponseDTO;
	}
}
