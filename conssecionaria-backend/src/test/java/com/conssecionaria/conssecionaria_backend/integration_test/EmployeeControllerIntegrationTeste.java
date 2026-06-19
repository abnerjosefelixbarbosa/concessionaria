package com.conssecionaria.conssecionaria_backend.integration_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.Employee;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;
import com.conssecionaria.conssecionaria_backend.model.repository.EmployeeRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTeste {
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

	// test para registrar funcionario

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

	// teste para atualizar funcionario pelo id

	@Test
	void shouldUpdateEmployeeByIdAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenNameIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO(null, "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenNameIsEmptyAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateEmployeeByIdWhenNameContains101CharactersAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ter até 100 caracteres.")).andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateEmployeeByIdWhenNameIsRepeatedCharactersAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("name1", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenMatriculationIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", null, "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ser obrigatória."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenMatriculationIsEmptyAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenMatriculationNotContains10CharactersAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenMatriculationContainsLettersAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "a222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void shouldNotUpdateEmployeeByIdWhenMatriculationIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "1111111111", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmailIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", null, "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmailIsEmptyAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmailIsInvalidAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenPhoneIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", null,
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenPhoneIsEmptyAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenPhoneContains31CharactersAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com",
				"8192222222222222222222222222222", LocalDate.now().withYear(1991), "02370962429",
				new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres."))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void shouldNotUpdateEmployeeByIdWhenPhoneIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com",
				"81911111111", LocalDate.now().withYear(1991), "02370962429",
				new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenBirthDateIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222", null,
				"02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.birthDate").value("Data de nascimento deve ser obrigatória."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenCPFIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), null, new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.cpf").value("CPF deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenCPFIsEmptyAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.cpf").value("CPF deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenCPFIsInvalidAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962421", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.cpf").value("CPF deve ser valido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateEmployeeByIdWhenCPFIsRepeatedAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenSalaryIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", null, 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.salary").value("Salário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenSalaryNotContains2DigitsAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("0"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Salário deve ter 2 dígitos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenSalaryIsZeroAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("0.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Salário não deve 0.00.")).andExpect(status().isBadRequest())
				.andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmployeeTypeIsSallerAndCommissionIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser obrigatório para funcionário vendedor."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmployeeTypeIsSallerAndCommissionIsZeroAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 0, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser obrigatório para funcionário vendedor."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmployeeTypeIsSallerAndCommissionIs101AndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 101, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser menor que 100."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmployeeStatusIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, null,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.employeeStatus").value("Status do funcionário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeByIdWhenEmployeeTypeIsNullAndReturnStatus400() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.employeeType").value("Tipo de funcionário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	// teste para procurar funcionario pelo id

	@Test
	void shouldFindEmployeeByIdAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		mockMvc.perform(get("/employees/find-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	void shouldNotFindEmployeeByIdWhenIdIsNotExistAndReturnStatus404() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		mockMvc.perform(get("/employees/find-employee-by-id/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").value("Id deve ser existente."))
				.andExpect(status().isNotFound()).andDo(print());
	}

	// teste para listar funcionários

	@Test
	void shouldListEmployeesAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		Employee employee2 = new Employee(null, "name2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "43468998465", new BigDecimal("2500.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER, null);

		Employee employee3 = new Employee(null, "name3", "3333333333", "email3@gmail.com", "81933333333",
				LocalDate.now().withYear(1991), "27406772432", new BigDecimal("2000.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.ASSISTANT_MANAGER, null);

		Employee employee4 = new Employee(null, "name4", "4444444444", "email4@gmail.com", "81944444444",
				LocalDate.now().withYear(1991), "66134259403", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1, employee2, employee3, employee4);

		employeeRepository.saveAll(employees);

		mockMvc.perform(get("/employees/list-employees").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.totalElements").value(4))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListEmployeesWhenNameContainsNameAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		Employee employee2 = new Employee(null, "name2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "43468998465", new BigDecimal("2500.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER, null);

		Employee employee3 = new Employee(null, "name3", "3333333333", "email3@gmail.com", "81933333333",
				LocalDate.now().withYear(1991), "27406772432", new BigDecimal("2000.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.ASSISTANT_MANAGER, null);

		Employee employee4 = new Employee(null, "name4", "4444444444", "email4@gmail.com", "81944444444",
				LocalDate.now().withYear(1991), "66134259403", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1, employee2, employee3, employee4);

		employeeRepository.saveAll(employees);

		mockMvc.perform(get("/employees/list-employees").queryParam("name", "name")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(4)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListEmployeesWhenEmployeeStatusIsActiveAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		Employee employee2 = new Employee(null, "name2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "43468998465", new BigDecimal("2500.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER, null);

		Employee employee3 = new Employee(null, "name3", "3333333333", "email3@gmail.com", "81933333333",
				LocalDate.now().withYear(1991), "27406772432", new BigDecimal("2000.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.ASSISTANT_MANAGER, null);

		Employee employee4 = new Employee(null, "name4", "4444444444", "email4@gmail.com", "81944444444",
				LocalDate.now().withYear(1991), "66134259403", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1, employee2, employee3, employee4);

		employeeRepository.saveAll(employees);

		mockMvc.perform(get("/employees/list-employees").queryParam("employeeStatus", "ACTIVE")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(4)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListEmployeesWhenEmployeeTypeIsSallerAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		Employee employee2 = new Employee(null, "name2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "43468998465", new BigDecimal("2500.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER, null);

		Employee employee3 = new Employee(null, "name3", "3333333333", "email3@gmail.com", "81933333333",
				LocalDate.now().withYear(1991), "27406772432", new BigDecimal("2000.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.ASSISTANT_MANAGER, null);

		Employee employee4 = new Employee(null, "name4", "4444444444", "email4@gmail.com", "81944444444",
				LocalDate.now().withYear(1991), "66134259403", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1, employee2, employee3, employee4);

		employeeRepository.saveAll(employees);

		mockMvc.perform(get("/employees/list-employees").queryParam("employeeType", "SALLER")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(2)).andExpect(status().isOk()).andDo(print());
	}
}