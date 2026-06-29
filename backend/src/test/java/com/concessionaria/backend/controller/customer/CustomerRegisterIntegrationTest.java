package com.concessionaria.backend.controller.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
public class CustomerRegisterIntegrationTest {
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
	void shouldRegisterCustomerAndReturnStatus201() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void shouldNotRegisterCustomerWhenNameIsNullAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO(null, "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotRegisterCustomerWhenNameIsEmptyAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("", "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotRegisterCustomerWhenNameIsRepeatedAndReturnStatus400() throws Exception {
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
	void shouldNotRegisterCustomerWhenDocumentIsNullAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", null, "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotRegisterCustomerWhenDocumentIsEmptyAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotRegisterCustomerWhenDocumentIsInvalidCPFAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743461", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	void shouldNotRegisterCustomerWhenDocumentIsInvalidCNPJAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "31729421000180", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenDocumentIsRepeatedAndReturnStatus400() throws Exception {
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
	void shouldNotRegisterCustomerWhenEmailIsNullAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", null, "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenEmailIsEmptyAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenEmailIsInvalidAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser valido."))
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenEmailIsRepeatedAndReturnStatus400() throws Exception {
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
	void shouldNotRegisterCustomerWhenPhoneIsNullAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenPhoneIsEmptyAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenPhoneContains31CharactersAndReturnStatus400() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "8191111111111111111111111111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres."))
				.andDo(print());
	}
	
	@Test
	void shouldNotRegisterCustomerWhenPhoneIsRepeatedAndReturnStatus400() throws Exception {
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
