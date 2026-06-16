package com.conssecionaria.conssecionaria_backend.controller;

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

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;
import com.conssecionaria.conssecionaria_backend.model.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "registra um funcionário."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado."), })
	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(summary = "registra funcionário.", description = "registra um funcionário.")
	@PostMapping(value = "/register-employee")
	public ResponseEntity<EmployeeResponseDTO> registerEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {
		EmployeeResponseDTO response = employeeService.registerEmployee(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "atualiza um funcionário."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado."), })
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "atualiza funcionário pelo id.", description = "atualiza um funcionário pelo id.")
	@PutMapping(value = "/update-employee-by-id/{id}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployeeById(@PathVariable String id,
			@RequestBody @Valid EmployeeRequestDTO dto) {
		EmployeeResponseDTO response = employeeService.updateEmployeeById(id, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "procura um funcionário pelo id."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado."), })
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "procura funcionário pelo id.", description = "procura um funcionário pelo id.")
	@GetMapping(value = "/find-employee-by-id/{id}")
	public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@PathVariable String id) {
		EmployeeResponseDTO response = employeeService.findEmployeeById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista um funcionário pelo nome, status do funcionário ou tipo do funcionário."),
			@ApiResponse(responseCode = "400", description = "retorna um erro de requesição."),
			@ApiResponse(responseCode = "404", description = "retorna um erro de conteudo não encontrado."), })
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "lista funcionário pelo nome, status do funcionário ou tipo do funcionário.", description = "lista um funcionário pelo nome, status do funcionário ou tipo do funcionário.")
	@GetMapping(value = "/list-employees-by-name-employeestatus-or-employeetype")
	public ResponseEntity<Page<EmployeeResponseDTO>> listEmployeesByNameEmployeeStatusOrEmployeeType(Pageable pageable,
			@RequestParam(required = false) String name, @RequestParam(required = false) EmployeeStatus employeeStatus,
			@RequestParam(required = false) EmployeeType employeeType) {
		Page<EmployeeResponseDTO> response = employeeService.listEmployees(pageable, name, employeeStatus,
				employeeType);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}