package com.concessionaria.backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta do funcionário.")
public record EmployeeResponseDTO(
		@Schema(description = "Id do funcionário.")
		String id,
		@Schema(description = "Nome do funcionário.")
		String name,
		@Schema(description = "Matrícula do funcionário.")
		String matriculation,
		@Schema(description = "Email do funcionário.")
		String email,
		@Schema(description = "Telefone do funcionário.")
		String phone,
		@Schema(description = "Data de nascimento do funcionário.")
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birthDate,
		@Schema(description = "CPF do funcionário.")
		String cpf,
		@Schema(description = "Salário do funcionário.")
		BigDecimal salary,
		@Schema(description = "Comissão do funcionário.")
		Integer commission,
		@Schema(description = "Status do funcionário do funcionário.")
		EmployeeStatus employeeStatus,
		@Schema(description = "Tipo do funcionário do funcionário.")
		EmployeeType employeeType
) {}