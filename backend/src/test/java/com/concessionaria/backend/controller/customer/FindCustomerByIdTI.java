package com.concessionaria.backend.controller.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.repository.CustomerRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FindCustomerByIdTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CustomerRepository customerRepository;

	@BeforeEach
	void setUp() {
		customerRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		customerRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Should find customer by id and return status 200.")
	void findCustomerByIdTest1() throws Exception {
		Customer customer = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		mockMvc.perform(get("/customers/find-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@DisplayName("Should not find customer by id when id is not existent and return status 404.")
	void findCustomerByIdTest2() throws Exception {
		Customer customer = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		mockMvc.perform(get("/customers/find-customer-by-id/1" + id))
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andExpect(status().isNotFound())
				.andDo(print());
	}
}