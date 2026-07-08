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

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.dto.BrandResponseDTO;
import com.concessionaria.backend.model.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/brands")
public class BrandController {
	private final BrandService brandService;

	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "registra uma marca."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@Operation(summary = "registrar marca.", description = "registra uma marca.")
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/register-brand")
	public ResponseEntity<BrandResponseDTO> registerBrand(@RequestBody @Valid BrandRequestDTO dto) {
		BrandResponseDTO response = brandService.registerBrand(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "atualiza uma marca pelo id."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@Operation(summary = "atualizar marca pelo id.", description = "atualiza uma marca pelo id.")
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(value = "/update-brand-by-id/{id}")
	public ResponseEntity<BrandResponseDTO> updateBrandById(@PathVariable String id,
			@RequestBody @Valid BrandRequestDTO dto) {
		BrandResponseDTO response = brandService.updateBrandById(id, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "procura uma marca pelo id."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@Operation(summary = "procurar marca pelo id.", description = "procura uma marca pelo id.")
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/find-brand-by-id/{id}")
	public ResponseEntity<BrandResponseDTO> findBrandById(@PathVariable String id) {
		BrandResponseDTO response = brandService.findBrandById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/list-brands-filtered-by-name")
	public ResponseEntity<Page<BrandResponseDTO>> listBrandsFilteredByName(@RequestParam(defaultValue = "") String name, Pageable pageable) {
		Page<BrandResponseDTO> response = brandService.listBrandsFilteredByName(name, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
