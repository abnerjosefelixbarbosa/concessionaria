package com.concessionaria.backend.model.service;

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.dto.ModelResponseDTO;

public interface ModelService {
	ModelResponseDTO registerModel(ModelRequestDTO dto);

	ModelResponseDTO updateModelById(String id, ModelRequestDTO dto);
}
