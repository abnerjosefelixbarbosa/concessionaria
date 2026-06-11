package com.conssecionaria.conssecionaria_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
	@NotEmpty(message = "Nome deve ser obrigatório.")
	@NotNull(message = "Nome deve ser obrigatório.")
	@Size(message = "Nome deve ter até 100 caracteres.", max = 100)
	private String name;
	@NotNull(message = "Matrícula deve ser obrigatória.")
	@Pattern(message = "Matrícula deve ter 10 caracteres numéricos.", regexp = "^\\d{10}$")
	private String matriculation;
	@NotEmpty(message = "Email deve ser valido.")
	@NotNull(message = "Email deve ser valido.")
	@Email(message = "Email deve ser valido.")
	private String email;
	@NotEmpty(message = "Telefone deve ser obrigatório.")
	@NotNull(message = "Telefone deve ser obrigatório.")
	@Size(message = "Telefone deve ter até 30 caracteres.", max = 30)
	private String phone;
	@NotNull(message = "Data de nascimento deve ser obrigatória.")
	private LocalDate birthDate;
	@NotNull(message = "CPF deve ser valido.")
	@CPF(message = "CPF deve ser valido.")
	private String cpf;
	@NotNull(message = "Salário deve ser obrigatório.")
	private BigDecimal salary;
	private Integer commission;
	@NotNull(message = "Status do funcionário deve ser obrigatório.")
	private EmployeeStatus employeeStatus;
	@NotNull(message = "Tipo de funcionário deve ser obrigatório.")
	private EmployeeType employeeType;
}