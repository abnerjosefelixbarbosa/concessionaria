package com.concessionaria.backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de requisição do funcionário.")
public record EmployeeRequestDTO(
		@Schema(description = "Nome do funcionário.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotEmpty(message = "Nome deve ser obrigatório.")
		@NotNull(message = "Nome deve ser obrigatório.")
		@Size(message = "Nome deve ter até 100 caracteres.", max = 100)
		String name,
		@Schema(description = "Matrícula do funcionário.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Matrícula deve ser obrigatória.")
		@Pattern(message = "Matrícula deve ter 10 caracteres numéricos.", regexp = "^\\d{10}$")
		 String matriculation,
		@Schema(description = "Email do funcionário.", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotEmpty(message = "Email deve ser valido.")
		@NotNull(message = "Email deve ser valido.")
		@Email(message = "Email deve ser valido.")
		String email,
		@Schema(description = "Telefone do funcionário", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotEmpty(message = "Telefone deve ser obrigatório.")
		@NotNull(message = "Telefone deve ser obrigatório.")
		@Size(message = "Telefone deve ter até 30 caracteres.", max = 30)
		String phone,
		@Schema(description = "Data de nascimento do funcionário", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Data de nascimento deve ser obrigatória.")
		LocalDate birthDate,
		@Schema(description = "CPF do funcionário", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "CPF deve ser valido.")
		@CPF(message = "CPF deve ser valido.")
		String cpf,
		@Schema(description = "Salário do funcionário", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Salário deve ser obrigatório.")
		BigDecimal salary,
		@Schema(description = "Comissão do funcionário", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
		Integer commission,
		@Schema(description = "Status do funcionário", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Status do funcionário deve ser obrigatório.")
		EmployeeStatus employeeStatus,
		@Schema(description = "Tipo do funcionário", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Tipo de funcionário deve ser obrigatório.")
		EmployeeType employeeType
) {

}
