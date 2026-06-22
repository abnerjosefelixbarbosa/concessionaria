package com.conssecionaria.conssecionaria_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de resposta do funcionário.")
public class EmployeeResponseDTO {
	@Schema(description = "Id do funcionário.")
	private String id;
	@Schema(description = "Nome do funcionário.")
	private String name;
	@Schema(description = "Matrícula do funcionário.")
	private String matriculation;
	@Schema(description = "Email do funcionário.")
	private String email;
	@Schema(description = "Telefone do funcionário.")
	private String phone;
	@Schema(description = "Data de nascimento do funcionário.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	@Schema(description = "CPF do funcionário.")
	private String cpf;
	@Schema(description = "Salário do funcionário.")
	private BigDecimal salary;
	@Schema(description = "Comissão do funcionário.")
	private Integer commission;
	@Schema(description = "Status do funcionário do funcionário.")
	private EmployeeStatus employeeStatus;
	@Schema(description = "Tipo do funcionário do funcionário.")
	private EmployeeType employeeType;
}