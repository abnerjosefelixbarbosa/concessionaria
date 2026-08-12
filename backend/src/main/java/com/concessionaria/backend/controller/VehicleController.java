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
import com.concessionaria.backend.model.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {
	private final VehicleService vehicleService;

	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/register-vehicle")
	public ResponseEntity<VehicleResponseDTO> registerVehicle(@RequestBody @Valid VehicleRequestDTO dto) {
		VehicleResponseDTO response = vehicleService.registerVehicle(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(value = "/update-vehicle-by-id/{id}")
	public ResponseEntity<VehicleResponseDTO> updateVehicleById(@PathVariable String id,
			@RequestBody @Valid VehicleRequestDTO dto) {
		VehicleResponseDTO response = vehicleService.updateVehicleById(id, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/find-vehicle-by-id/{id}")
	public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable String id) {
		VehicleResponseDTO response = vehicleService.findVehicleById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/list-vehicle-by-transmission-type-and-price")
	public ResponseEntity<Page<VehicleResponseDTO>> listVehicleByTransmissionTypeAndPrice(
			@RequestParam(defaultValue = "") TransmissionType transmissionType, @RequestParam(defaultValue = "") BigDecimal price,
			Pageable pageable) {
		Page<VehicleResponseDTO> response = vehicleService.listVehicleByTransmissionTypeAndPrice(transmissionType, price, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}