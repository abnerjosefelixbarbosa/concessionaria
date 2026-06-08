package com.conssecionaria.conssecionaria_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;

public record EmployeeResponseDTO(
		String id,
		String name,
		String matriculation,
		String email,
		String phone,
		LocalDate birthDate,
		String cpf,
		BigDecimal salary,
		Integer commission,
		EmployeeStatus employeeStatus,
		EmployeeType employeeType
) {}