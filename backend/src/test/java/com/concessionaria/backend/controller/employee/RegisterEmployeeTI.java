package com.concessionaria.backend.controller.employee;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class RegisterEmployeeTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() {
		employeeRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		employeeRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Should register employee and return status 201.")
	void registerEmployeeTest1() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@DisplayName("Should not register employee when name is null and return status 400.")
	void registerEmployeeTest2() throws Exception {
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
	@DisplayName("Should not register employee when name is empty and return status 400.")
	void registerEmployeeTest3() throws Exception {
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
	@DisplayName("Should not register employee  when name contains 101 characters and return status 400.")
	void registerEmployeeTest4() throws Exception {
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
	@DisplayName("Should not register employee when name is repeated and return status 400.")
	void registerEmployeeTest5() throws Exception {
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
	@DisplayName("Should not register employee when matriculation is null and return status 400.")
	void registerEmployeeTest6() throws Exception {
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
	@DisplayName("Should not register employee when matriculation is empty and return status 400.")
	void registerEmployeeTest7() throws Exception {
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
	@DisplayName("Should not register employee when matriculation not contains 10 numeric characters and return status 400")
	void registerEmployeeTest8() throws Exception {
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
	@DisplayName("Should not register employee when matriculation contains letters and return status 400.")
	void registerEmployeeTest9() throws Exception {
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
	@DisplayName("Should not register employee when matriculation is repeated and return status 400.")
	void registerEmployeeTest10() throws Exception {
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
	@DisplayName("Should not register employee when email is null and return status 400.")
	void registerEmployeeTest11() throws Exception {
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
	@DisplayName("Should not register employee when email is empty and return status 400.")
	void registerEmployeeTest12() throws Exception {
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
	@DisplayName("Should not register employee when email is invalid and return status 400.")
	void registerEmployeeTest13() throws Exception {
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
	@DisplayName("Should not register employee when email is repeated and return status 400.")
	void registerEmployeeTest14() throws Exception {
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
	@DisplayName("Should not register employee when phone is null and return status 400.")
	void registerEmployeeTest15() throws Exception {
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
	@DisplayName("Should not register employee when phone is empty and return status 400.")
	void registerEmployeeTest16() throws Exception {
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
	@DisplayName("Should not register employee when phone contains 31 characters and return status 400.")
	void registerEmployeeTest17() throws Exception {
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
	@DisplayName("Should not register employee when phone is repeated and return status 400.")
	void RegisterEmployeeTest18() throws Exception {
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
	@DisplayName("Should not register employee when birth date is null and return status 400.")
	void registerEmployeeTest19() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111", null,
				"09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.birthDate").value("Data de nascimento deve ser obrigatória."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not register employee when CPF is null and return status 400.")
	void registerEmployeeTest20() throws Exception {
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
	@DisplayName("Should not register employee when CPF is empty and return status 400.")
	void registerEmployeeTest21() throws Exception {
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
	@DisplayName("Should not register employee when CPF is invalid and return status 400.")
	void registerEmployeeTest22() throws Exception {
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
	@DisplayName("Should not register employee when CPF is repeated and return status 400.")
	void registerEmployeeTest23() throws Exception {
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
	@DisplayName("Should not register employee when salary is null and return status 400.")
	void registerEmployeeTest24() throws Exception {
		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", null, 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees/register-employee").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.salary").value("Salário deve ser obrigatório."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not register employee when salary not contains 2 digits and return status 400.")
	void registerEmployeeTest25() throws Exception {
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
	@DisplayName("Should not register employee when salary is '0.00' and return status 400.")
	void registerEmployeeTest26() throws Exception {
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
	@DisplayName("Should not register mployee when comissition is null and employee type is saller and return status 400.")
	void registerEmployeeTest27() throws Exception {
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
	@DisplayName("Should not register employee when comissition is 0 and employee type is saller and return status 400.")
	void registerEmployeeTest28() throws Exception {
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
	@DisplayName("Should not register employee when comissition is '101' and employee type is saller and return status 400.")
	void registerEmployeeTest29()
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
	@DisplayName("Should not register employee when employee status is null and return status 400.")
	void registerEmployeeTest30() throws Exception {
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
	@DisplayName("Should not register employee when employee type is null and return status 400.")
	void registerEmployeeTest31() throws Exception {
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