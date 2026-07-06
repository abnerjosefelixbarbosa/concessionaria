package com.concessionaria.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;
import com.concessionaria.backend.model.service.BrandService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/brands")
public class BrandController {
	private final BrandService brandService;

	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/register-brand")
	public ResponseEntity<BrandResponseDTO> registerBrand(@RequestBody @Valid BrandRequestDTO dto) {
		BrandResponseDTO response = brandService.registerBrand(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(value = "/update-brand-by-id/{id}")
	public ResponseEntity<BrandResponseDTO> updateBrandById(@PathVariable String id, @RequestBody @Valid BrandRequestDTO dto) {
		BrandResponseDTO response = brandService.updateBrandById(id, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
