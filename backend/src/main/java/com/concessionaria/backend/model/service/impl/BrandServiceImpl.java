package com.concessionaria.backend.model.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.BrandMapper;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.service.BrandService;

import jakarta.transaction.Transactional;

@Service
public class BrandServiceImpl implements BrandService {
	private final BrandRepository brandRepository;

	public BrandServiceImpl(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Transactional
	public BrandResponseDTO registerBrand(BrandRequestDTO dto) {
		Brand brand = BrandMapper.toBrand(dto);

		validateBrand(brand);

		Brand brandSave = brandRepository.save(brand);

		return BrandMapper.toBrandResponseDTO(brandSave);
	}

	@Transactional
	public BrandResponseDTO updateBrandById(String id, BrandRequestDTO dto) {
		Brand brand = BrandMapper.toBrand(dto);

		validateBrand(brand);

		Brand brandFound = brandRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));
		
		BeanUtils.copyProperties(brand, brandFound, "id");
		
		brandRepository.save(brandFound);

		return BrandMapper.toBrandResponseDTO(brandFound);
	}

	private void validateBrand(Brand brand) {
		if (isExistsByName(brand)) {
			throw new ApplicationException("Nome deve não deve ser repetido.");
		}
	}

	private boolean isExistsByName(Brand brand) {
		return brandRepository.existsByName(brand.getName());
	}
}
