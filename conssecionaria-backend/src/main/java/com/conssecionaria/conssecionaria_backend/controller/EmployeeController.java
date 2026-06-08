package com.conssecionaria.conssecionaria_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> registerEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {
		EmployeeResponseDTO response = this.employeeService.registerEmployee(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}