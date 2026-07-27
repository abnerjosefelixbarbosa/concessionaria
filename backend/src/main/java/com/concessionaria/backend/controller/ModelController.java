package com.concessionaria.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.dto.ModelResponseDTO;
import com.concessionaria.backend.model.service.ModelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/models")
public class ModelController {
	private final ModelService modelService;

	public ModelController(ModelService modelService) {
		this.modelService = modelService;
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/register-model")
	public ResponseEntity<ModelResponseDTO> registerModel(@RequestBody @Valid ModelRequestDTO dto) {
		ModelResponseDTO response = modelService.registerModel(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(value = "/update-model-by-id/{id}")
	public ResponseEntity<ModelResponseDTO> updateModelById(@PathVariable String id, @RequestBody @Valid ModelRequestDTO dto) {
		ModelResponseDTO response = modelService.updateModelById(id, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/find-model-by-id/{id}")
	public ResponseEntity<ModelResponseDTO> updateModelById(@PathVariable String id) {
		ModelResponseDTO response = modelService.findModelById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/list-models-filtered-by-name")
	public ResponseEntity<Page<ModelResponseDTO>> listModelsFilteredByName(@RequestParam(defaultValue = "") String name, Pageable pageable) {
		Page<ModelResponseDTO> response = modelService.listModelsFilteredByName(name, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}