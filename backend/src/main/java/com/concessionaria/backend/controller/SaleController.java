package com.concessionaria.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.concessionaria.backend.model.dto.SaleRequestDTO;
import com.concessionaria.backend.model.dto.SaleResponseDTO;
import com.concessionaria.backend.model.service.SaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {
	private final SaleService saleService;

	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<SaleResponseDTO> registerSale(@RequestBody @Valid SaleRequestDTO dto) {
		SaleResponseDTO response = saleService.registerSale(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleterSaleById(@PathVariable String id) {
		saleService.deleterSaleById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}