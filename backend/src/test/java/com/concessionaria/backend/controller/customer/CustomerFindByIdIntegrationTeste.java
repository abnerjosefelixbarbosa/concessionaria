package com.concessionaria.backend.controller.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.repository.CustomerRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerFindByIdIntegrationTeste {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
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
	void shouldFindCustomerByIdAndReturnStatus200() throws Exception {
		Customer customer = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "26516743460", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(get("/customers/find-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldNotFindCustomerByIdWhenIdIsNonExistentAndReturnStatus404() throws Exception {
		Customer customer = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		mockMvc.perform(get("/customers/find-customer-by-id/1" + id))
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andExpect(status().isNotFound())
				.andDo(print());
	}
}
