package com.concessionaria.backend.controller.employee;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class EmployeeUpdateNameByIdIntegrationTest {
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
	void shouldUpdateEmployeeNameByIdAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		String id = employeeRepository.save(employee1).getId();

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome2", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(patch("/employees/update-employee-name-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldNotUpdateEmployeeNameByIdWhenNameIsRepeatedAndReturnStatus200() throws Exception {
		Employee employee1 = new Employee(null, "nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1991), "09458274400", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		String id = employeeRepository.save(employee1).getId();

		EmployeeRequestDTO dto = new EmployeeRequestDTO("nome1", "2222222222", "email2@gmail.com", "81922222222",
				LocalDate.now().withYear(1991), "02370962429", new BigDecimal("1500.00"), 100, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(patch("/employees/update-employee-name-by-id/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(
						jsonPath("$.message").value("Nome, matrícula, email, telefone ou cpf não deve ser repetido."))
				.andDo(print());
	}
}
