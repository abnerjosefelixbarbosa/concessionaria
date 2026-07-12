package com.concessionaria.backend.model.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.dto.ModelResponseDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.ModelMapper;
import com.concessionaria.backend.model.repository.ModelRepository;
import com.concessionaria.backend.model.service.BrandService;
import com.concessionaria.backend.model.service.ModelService;

import jakarta.transaction.Transactional;

@Service
public class ModelServiceImpl implements ModelService {
	private final ModelRepository modelRepository;
	private final BrandService brandService;

	public ModelServiceImpl(ModelRepository modelRepository, BrandService brandService) {
		this.modelRepository = modelRepository;
		this.brandService = brandService;
	}

	@Transactional
	public ModelResponseDTO registerModel(ModelRequestDTO dto) {
		Model model = ModelMapper.toModel(dto);

		validadeModel(model);

		Brand brand = brandService.findByName(model.getBrand().getName());

		model.setBrand(brand);

		Model modelSave = modelRepository.save(model);

		return ModelMapper.toModelResponseDTO(modelSave);
	}

	@Transactional
	public ModelResponseDTO updateModelById(String id, ModelRequestDTO dto) {
		Model model = ModelMapper.toModel(dto);

		validadeModel(model);

		Brand brand = brandService.findByName(model.getBrand().getName());

		model.setBrand(brand);

		Model modelFound = modelRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		BeanUtils.copyProperties(model, modelFound, "id");

		modelRepository.save(modelFound);

		return ModelMapper.toModelResponseDTO(modelFound);
	}

	private void validadeModel(Model model) {
		boolean isExistsByName = modelRepository.existsByName(model.getName());

		if (isExistsByName) {
			throw new ApplicationException("Nome não deve ser repetido.");
		}
	}
}
