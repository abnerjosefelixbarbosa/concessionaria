package com.conssecionaria.conssecionaria_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
	private String id;
	private String name;
	private String matriculation;
	private String email;
	private String phone;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	private String cpf;
	private BigDecimal salary;
	private Integer commission;
	private EmployeeStatus employeeStatus;
	private EmployeeType employeeType;
}