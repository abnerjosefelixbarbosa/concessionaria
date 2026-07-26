package com.concessionaria.backend.controller.employee;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
class UpdateEmployeeByIdTI {
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
	@DisplayName("Should update employee by id and return status 200.")
	void updateEmployeeByIdTest1() throws Exception {
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
	@DisplayName("Should not update employee by id when id is not existent and return status 404.")
	void updateEmployeeByIdTest2() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/1" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.message").value("Id deve ser existente."))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	@DisplayName("Should not update employee by id when name is null and return status 400.")
	void updateEmployeeByIdTest3() throws Exception {
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
	@DisplayName("Should not update employee by id when name is empty and return status 400.")
	void updateEmployeeByIdTest4() throws Exception {
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
	@DisplayName("Should not update employee by id when name contains 101 characters and return status 400.")
	void updateEmployeeByIdTest5() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO(
				"name1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
				"2222222222", "email2@gmail.com", "81922222222", LocalDate.now().withYear(1991), "02370962429",
				new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE, EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.name").value("Nome deve ter até 100 caracteres."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not update employee by id when name is repeated characters and return status 400.")
	void updateEmployeeByIdTest6() throws Exception {
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
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not update employee by id when matriculation is null and return status 400.")
	void updateEmployeeByIdTest7() throws Exception {
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
	@DisplayName("Should not update employee by id when matriculation is empty and return status 400.")
	void updateEmployeeByIdTest8() throws Exception {
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
	@DisplayName("Should not update employee by id when matriculation not contains 10 characters and return status 400.")
	void updateEmployeeByIdTest9() throws Exception {
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
	@DisplayName("Should not update employee by id when matriculation contains letters and return status 400.")
	void updateEmployeeByIdTest10() throws Exception {
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
	@DisplayName("Should not update employee by id when matriculation is repeated and return status 400.")
	void updateEmployeeByIdTest11() throws Exception {
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
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not update employee by id when email is null and return status 400.")
	void updateEmployeeByIdTest12() throws Exception {
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
	@DisplayName("Should not update employee by id when email is empty and return status 400.")
	void updateEmployeeByIdTest13() throws Exception {
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
	@DisplayName("Should not update employee by id when email is invalid and return status 400.")
	void updateEmployeeByIdTest14() throws Exception {
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
	@DisplayName("Should not update employee by id when phone is null and return status 400.")
	void updateEmployeeByIdTest15() throws Exception {
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
	@DisplayName("Should not update employee by id when phone is empty and return status 400.")
	void updateEmployeeByIdTest16() throws Exception {
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
	@DisplayName("Should not update employee by id when phone contains 31 characters and return status 400.")
	void updateEmployeeByIdTest17() throws Exception {
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
	@DisplayName("Should not update employee by id when phone is repeated and return status 400.")
	void updateEmployeeByIdTest18() throws Exception {
		Employee employee1 = new Employee(null, "name1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		List<Employee> employees = List.of(employee1);

		employeeRepository.saveAll(employees);

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/employees/update-employee-by-id/" + employees.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not update employee by id when birth date is null and return status 400.")
	void updateEmployeeByIdTest19() throws Exception {
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
	@DisplayName("Should not update employee by id when CPF is null and return status 400.")
	void updateEmployeeByIdTest20() throws Exception {
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
	@DisplayName("Should not update employee by id when CPF is empty and return status 400.")
	void updateEmployeeByIdTest21() throws Exception {
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
	@DisplayName("Should not update employee by id when CPF is invalid and return status 400.")
	void updateEmployeeByIdTest22() throws Exception {
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
	@DisplayName("Should not update employee by id when CPF is repeated and return status 400.")
	void updateEmployeeByIdTest23() throws Exception {
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
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	@DisplayName("Should not update employee by id when salary is null and return status 400.")
	void updateEmployeeByIdTest24() throws Exception {
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
	@DisplayName("Should not update employee by id when salary not contains 2 digits and return status 400.")
	void updateEmployeeByIdTest25() throws Exception {
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
	@DisplayName("Should not update employee by id when salary is '0.00' and return status 400.")
	void updateEmployeeByIdTest26() throws Exception {
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
	@DisplayName("Should not update employee by id when employee type is saller and commission is null and return status 400.")
	void updateEmployeeByIdTest27() throws Exception {
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
	@DisplayName("Should not update employee by id when employee type is saller and commission is zero and return status 400.")
	void updateEmployeeByIdTest28() throws Exception {
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
	@DisplayName("Should not update employee by id when employee type is saller and commission is 101 and return status 400.")
	void updateEmployeeByIdTest29() throws Exception {
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
	@DisplayName("Should not update employee by id when employee status is null and return status 400.")
	void updateEmployeeByIdTest30() throws Exception {
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
	@DisplayName("Should not update employee by id when employee type is null and return status 400.")
	void updateEmployeeByIdTest31() throws Exception {
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
}