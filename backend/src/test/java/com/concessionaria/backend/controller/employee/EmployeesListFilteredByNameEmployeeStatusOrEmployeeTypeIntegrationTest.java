package com.concessionaria.backend.controller.employee;

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

import com.concessionaria.backend.model.entity.Employee;
import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;
import com.concessionaria.backend.model.repository.EmployeeRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EmployeesListFilteredByNameEmployeeStatusOrEmployeeTypeIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
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
	void shouldListEmployeesFilteredByNameEmployeeStatusOrEmployeeTypeAndReturnStatus200() throws Exception {
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

		mockMvc.perform(get("/employees/list-employees-filtered-by-name-employee-status-or-employee-type").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.totalElements").value(4))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListEmployeesFilteredByNameEmployeeStatusOrEmployeeTypeWhenNameContainsNameAndReturnStatus200() throws Exception {
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

		mockMvc.perform(get("/employees/list-employees-filtered-by-name-employee-status-or-employee-type").queryParam("name", "name")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(4)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	void shouldListEmployeesFilteredByNameEmployeeStatusOrEmployeeTypeWhenEmployeeStatusIsActiveAndReturnStatus200() throws Exception {
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

		mockMvc.perform(get("/employees/list-employees-filtered-by-name-employee-status-or-employee-type").queryParam("employeeStatus", "ACTIVE")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(4)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListEmployeesFilteredByNameEmployeeStatusOrEmployeeTypeWhenEmployeeTypeIsSallerAndReturnStatus200() throws Exception {
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

		mockMvc.perform(get("/employees/list-employees-filtered-by-name-employee-status-or-employee-type").queryParam("employeeType", "SALLER")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(2)).andExpect(status().isOk()).andDo(print());
	}
}
