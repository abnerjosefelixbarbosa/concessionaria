package com.concessionaria.backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.dto.ModelResponseDTO;

public interface ModelService {
	ModelResponseDTO registerModel(ModelRequestDTO dto);

	ModelResponseDTO updateModelById(String id, ModelRequestDTO dto);
	
	ModelResponseDTO findModelById(String id);
	
	Page<ModelResponseDTO> listModelsFilteredByName(String name, Pageable pageable);
}