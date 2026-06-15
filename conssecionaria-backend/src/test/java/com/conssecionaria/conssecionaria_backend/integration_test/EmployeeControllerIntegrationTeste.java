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
import java.util.Formatter.BigDecimalLayoutForm;

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

	@Test
	void shouldRegisterEmployeeAndReturn201Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithNullNameAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithEmptyNameAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNameLongerThan100AndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO(
				"nome1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
				"1111111111", "email1@gmail.com", "81911111111", LocalDate.now().withYear(1991), "09458274400",
				new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ter até 100 caracteres."))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void shouldRegisterEmployeeWithRepeatedNameAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullMatriculationAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithEmptyMatriculationAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithMatriculationLongerThan10AndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "11111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.matriculation").value("Matrícula deve ter 10 caracteres numéricos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithMatriculationLessThan10AndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithMatriculationContainingLettersAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithRepeatedMatriculationAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullEmailAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithEmptyEmailAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithInvalidEmailAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithRepeatedEmailAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullPhoneAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithEmptyPhoneAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithPhoneLongerThan30AndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithRepeatedPhoneAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullBirthDateAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111", null,
				"09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.birthDate").value("Data de nascimento deve ser obrigatória."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithNullCPFAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithEmptyCPFAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithInvalidCPFAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithRepeatedCPFAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullSalaryAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", null, 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.salary").value("Salário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithSalaryLessThan2digitsAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("0.0"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Salário deve ter 2 dígitos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithSalaryLogerThan2digitsAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("0.000"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Salário deve ter 2 dígitos."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithSalaryContaining0AndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullComissitionAndNullEmployeeTypeSallerAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithComissitionContainint0AndEmployeeTypeSallerAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithComissitionLongerThan100AndEmployeeTypeSallerAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 101, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser menor que 100."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	// with a commission other than zero

	@Test
	void shouldRegisterEmployeeWithCommissionOtherThanNullAndEmployeeTypeSallerAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithCommissionOtherThanNullAndEmployeeTypeManagerAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 0, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser obrigatório para funcionário vendedor."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithCommissionOtherThanNullAndEmployeeTypeAssistantManagerAndReturn400Status()
			throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 0, EmployeeStatus.ACTIVE,
				EmployeeType.ASSISTANT_MANAGER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Comissão deve ser obrigatório para funcionário vendedor."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldRegisterEmployeeWithNullEmployeeStatusAndReturn400Status() throws Exception {
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
	void shouldRegisterEmployeeWithNullEmployeeTypeAndReturn400Status() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.employeeType").value("Tipo de funcionário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldUpdateEmployeeByIdAndReturn200Status() throws Exception {
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
	void shouldFindEmployeeByIdAndReturn200Status() throws Exception {
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
	void shouldListEmployeesByNameOrEmployeeStatusOrEmployeeTypeAndReturn200Status() throws Exception {
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

		mockMvc.perform(get("/employees/list-employees-by-name-or-employeestatus-or-employeetype")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}
}