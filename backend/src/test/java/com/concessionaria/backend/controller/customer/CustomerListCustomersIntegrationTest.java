package com.concessionaria.backend.controller.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class CustomerListCustomersIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CustomerRepository customerRepository;

	@BeforeEach
	void setUp() throws Exception {
		customerRepository.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
		customerRepository.deleteAll();
	}

	@Test
	void shouldListCustomersAndReturnStatus200() throws Exception {
		Customer customer1 = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		Customer customer2 = new Customer(null, "nome2", "81899898000183", "email2@gmail.com", "81922222222", null);

		customerRepository.save(customer1);

		customerRepository.save(customer2);

		mockMvc.perform(get("/customers/list-customers").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.totalElements").value(2))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListCustomersWhenNameContainsNomeAndReturnStatus200() throws Exception {
		Customer customer1 = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		Customer customer2 = new Customer(null, "nome2", "81899898000183", "email2@gmail.com", "81922222222", null);

		Customer customer3 = new Customer(null, "nome3", "60996653406", "email3@gmail.com", "81933333333", null);

		customerRepository.save(customer1);

		customerRepository.save(customer2);

		customerRepository.save(customer3);

		mockMvc.perform(get("/customers/list-customers").queryParam("name", "nome")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(3)).andExpect(status().isOk()).andDo(print());
	}
}
