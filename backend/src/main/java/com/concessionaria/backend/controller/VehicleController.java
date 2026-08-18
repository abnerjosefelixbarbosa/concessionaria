package com.concessionaria.backend.controller;

import java.math.BigDecimal;

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

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.dto.VehicleResponseDTO;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;
import com.concessionaria.backend.model.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {
	private final VehicleService vehicleService;

	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@Operation(summary = "registrar veículo.", description = "registra um veículo.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "registra um veículo."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<VehicleResponseDTO> registerVehicle(@RequestBody @Valid VehicleRequestDTO dto) {
		VehicleResponseDTO response = vehicleService.registerVehicle(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "atualizar veículo pelo id.", description = "atualiza um veículo pelo id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "atualiza um veículo pelo id."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public ResponseEntity<VehicleResponseDTO> updateVehicleById(@PathVariable String id,
			@RequestBody @Valid VehicleRequestDTO dto) {
		VehicleResponseDTO response = vehicleService.updateVehicleById(id, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(summary = "procurar veículo pelo id.", description = "procura um veículo pelo id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "procura um veículo pelo id."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable String id) {
		VehicleResponseDTO response = vehicleService.findVehicleById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(summary = "listar varios veículos.", description = "lista varios veículos.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "lista varios veículos."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado.") })
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public ResponseEntity<Page<VehicleResponseDTO>> listVehicles(
			@RequestParam(defaultValue = "") TransmissionType transmissionType,
			@RequestParam(defaultValue = "") BigDecimal price,
			@RequestParam(defaultValue = "") VehicleStatus vehicleStatus, @RequestParam(defaultValue = "") String color,
			@RequestParam(defaultValue = "") String plate, Pageable pageable) {
		Page<VehicleResponseDTO> response = vehicleService.listVehicles(transmissionType, price, vehicleStatus, color,
				plate, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}