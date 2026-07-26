package com.concessionaria.backend.controller.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.repository.CustomerRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RegisterCustomerTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
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
	@DisplayName("Should register customer and return status 201.")
	void registerCustomerTest1() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when name is null and return status 400.")
	void registerCustomerTest2() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO(null, "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when name is empty and return status 400.")
	void registerCustomerTest3() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("", "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when name contains 101 characters and return status 400.")
	void registerCustomerTest4() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO(
				"nome1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
				"26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ter até 100 caracteres.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when name is repeated and return status 400.")
	void registerCustomerTest5() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		customerRepository.save(customer);

		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when document is null and return status 400.")
	void registerCustomerTest6() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", null, "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when document is empty and return status 400.")
	void registerCustomerTest7() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when document is invalid CPF and return status 400.")
	void registerCustomerTest8() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743461", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when document is invalid CNPJ and return status 400.")
	void registerCustomerTest9() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "31729421000180", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when document is repeated and return status 400.")
	void registerCustomerTest10() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		customerRepository.save(customer);

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "26516743460", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when email is null and return status 400.")
	void registerCustomerTest11() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", null, "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when email is empty and return status 400.")
	void registerCustomerTest12() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when email is invalid and return status 400.")
	void registerCustomerTest13() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when email is repeated and return status 400.")
	void registerCustomerTest14() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		customerRepository.save(customer);

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email1@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when phone is null and return status 400.")
	void registerCustomerTest15() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when phone is empty and return status 400.")
	void registerCustomerTest16() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when phone contains 31 characters and return status 400.")
	void registerCustomerTest17() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com",
				"8191111111111111111111111111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres.")).andDo(print());
	}

	@Test
	@DisplayName("Should not register customer when phone is repeated and return status 400.")
	void registerCustomerTest18() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		customerRepository.save(customer);

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}
}