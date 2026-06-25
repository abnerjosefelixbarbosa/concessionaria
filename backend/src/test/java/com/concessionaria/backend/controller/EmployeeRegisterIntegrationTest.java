package com.concessionaria.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.concessionaria.backend.model.dto.EmployeeRequestDTO;
import com.concessionaria.backend.model.entity.Employee;
import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;
import com.concessionaria.backend.model.repository.EmployeeRepository;

import tools.jackson.databind.ObjectMapper;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EmployeeRegisterIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() throws Exception {
		employeeRepository.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
		employeeRepository.deleteAll();
	}
	
	@Test
	void shouldRegisterEmployeeAndReturnStatus201() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenNameIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO(null, "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenNameIsEmptyAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterEmployeeWhenNameContains101CharactersAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO(
				"nome1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
				"1111111111", "email1@gmail.com", "81911111111", LocalDate.now().withYear(1991), "09458274400",
				new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ter até 100 caracteres."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenNameIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name1", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "16073430450", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenMatriculationIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", null, "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ser obrigatória."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenMatriculationIsEmptyAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenMatriculationNotContains10NumericCharactersAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenMatriculationContainsLettersAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "a111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenMatriculationIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name2", "1111111111", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "16073430450", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenEmailIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", null, "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenEmailIsEmptyAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenEmailIsInvalidAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenEmailIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name2", "2222222222", "email1@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "16073430450", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenPhoneIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", null,
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenPhoneIsEmptyAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenPhoneContains31CharactersAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com",
				"8191111111111111111111111111111", LocalDate.now().withYear(1991), "09458274400",
				new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenPhoneIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name2", "2222222222", "email2@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "16073430450", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenBirthDateIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111", null,
				"09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.birthDate").value("Data de nascimento deve ser obrigatória."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenCPFIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), null, new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.cpf").value("CPF deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenCPFIsEmptyAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.cpf").value("CPF deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenCPFIsInvalidAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274401", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.cpf").value("CPF deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenCPFIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenSalaryIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", null, 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.salary").value("Salário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenSalaryNotContains2DigitsAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("0"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Salário deve ter 2 dígitos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenSalaryIsZeroAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("0.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Salário não deve 0.00.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenComissitionIsNullAndEmployeeTypeIsSallerAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser obrigatório para funcionário vendedor."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenComissitionIsZeroAndEmployeeTypeIsSallerAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 0, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser obrigatório para funcionário vendedor."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenComissitionIs101AndEmployeeTypeIsSallerAndReturnStatus400()
			throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 101, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser menor que 100."))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void shouldNotRegisterEmployeeWhenEmployeeStatusIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, null,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.employeeStatus").value("Status do funcionário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotRegisterEmployeeWhenEmployeeTypeIsNullAndReturnStatus400() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.employeeType").value("Tipo de funcionário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}
}
