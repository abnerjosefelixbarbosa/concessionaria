package com.concessionaria.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.dto.CustomerResponseDTO;
import com.concessionaria.backend.model.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "registra um cliente."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição.") })
	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(summary = "registra cliente.", description = "registra um cliente.")
	@PostMapping(value = "/register-customer")
	public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody @Valid CustomerRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.registerCustomer(dto));
	}
}
