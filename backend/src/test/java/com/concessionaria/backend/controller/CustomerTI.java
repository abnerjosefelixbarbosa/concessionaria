package com.concessionaria.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class CustomerTI {
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
	
	// register customer
	
	@Test
	@DisplayName("Should Register Customer And Return Status 201.")
	void registerCustomerTest1() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Name Is Null And Return Status 400.")
	void registerCustomerTest2() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO(null, "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Name Is Empty And Return Status 400.")
	void registerCustomerTest3() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("", "26516743460", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Name Contains 101 Characters And Return Status 400.")
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
	@DisplayName("Should Not Register Customer When Name Is Repeated And Return Status 400.")
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
	@DisplayName("Should Not Register Customer When Document Is Null And Return Status 400.")
	void registerCustomerTest6() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", null, "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Document Is Empty And Return Status 400.")
	void registerCustomerTest7() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Document Is Invalid CPF And Return Status 400.")
	void registerCustomerTest8() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743461", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Document Is Invalid CNPJ And Return Status 400.")
	void registerCustomerTest9() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "31729421000180", "email1@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Document Is Repeated And Return Status 400.")
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
	@DisplayName("Should Not Register Customer When Email Is Null And Return Status 400.")
	void registerCustomerTest11() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", null, "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Email Is Empty And Return Status 400.")
	void registerCustomerTest12() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Email Is Invalid And Return Status 400.")
	void registerCustomerTest13() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Email Is Repeated And Return Status 400.")
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
	@DisplayName("Should Not Register Customer When Phone Is Null And Return Status 400.")
	void registerCustomerTest15() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Phone Is Empty And Return Status 400.")
	void registerCustomerTest16() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com", "");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Phone Contains 31 Characters And Return Status 400.")
	void registerCustomerTest17() throws Exception {
		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "26516743460", "email1@gmail.com",
				"8191111111111111111111111111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/customers/register-customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Customer When Phone Is Repeated And Return Status 400.")
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
	
	// update customer by id
	
	@Test
	@DisplayName("Should Update Customer By Id And Return Status 200.")
	void updateCustomerByIdTest1() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Id Is Not Existent And Return Status 404.")
	void updateCustomerByIdTest2() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/1" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Name Is Null And Return Status 400.")
	void updateCustomerByIdTest3() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO(null, "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Name Is Empty And Return Status 400.")
	void updateCustomerByIdTest4() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Name Contains 101 Characters And Return Status 400.")
	void updateCustomerByIdTest5() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO(
				"nome1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
				"83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ter até 100 caracteres.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Name Is Repeated And Return Status 400.")
	void updateCustomerByIdTest6() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome1", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By  Id When Document Is Null And Return Status 400.")
	void updateCustomerByIdTest7() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", null, "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Document Is Empty And Return Status 400.")
	void updateCustomerByIdTest8() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Document Is Invalid CPF And Return Status 400.")
	void updateCustomerByIdTest9() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704451", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Document Is Invalid CNPJ And Return Status 400.")
	void updateCustomerByIdTest10() throws Exception {
		Customer customer = new Customer(null, "nome1", "23896519000103", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "29822617000171", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Document Is Repeated And Return Status 400.")
	void updateCustomerByIdTest11() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "26516743460", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Email Is Null And Return Status 400.")
	void updateCustomerByIdTest12() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", null, "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Email Is Empty And Return Status 400.")
	void updateCustomerByIdTest13() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Email Is Invalid And Return Status 400.")
	void updateCustomerByIdTest14() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email1gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser valido.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Email Is Repeated And Return Status 400.")
	void updateCustomerByIdTest15() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email1@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Phone Is Null And Return Status 400.")
	void updateCustomerByIdTest16() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Phone Is Empty And Return Status 400.")
	void updateCustomerByIdTest17() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Phone Contains 31 Characters And Return Status 400.")
	void updateCustomerByIdTest18() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com",
				"8192222222222222222222222222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Update Customer By Id When Phone Is Repeated And Return Status 400.")
	void updateCustomerByIdTest19() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}
	
	// find customer by id
	
	@Test
	@DisplayName("Should Find Customer By Id And Return Status 200.")
	void findCustomerByIdTest1() throws Exception {
		Customer customer = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "26516743460", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(get("/customers/find-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@DisplayName("Should Not Find Customer By Id When Id Is Not Existent And Return Status 404.")
	void findCustomerByIdTest2() throws Exception {
		Customer customer = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		mockMvc.perform(get("/customers/find-customer-by-id/1" + id))
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andExpect(status().isNotFound())
				.andDo(print());
	}
	
	// list customers filtered by name
	
	@Test
	@DisplayName("Should List Customers Filtered By Name And Return Status 200.")
	void listCustomersFilteredByNameTest1() throws Exception {
		Customer customer1 = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		Customer customer2 = new Customer(null, "nome2", "81899898000183", "email2@gmail.com", "81922222222", null);

		customerRepository.save(customer1);

		customerRepository.save(customer2);

		mockMvc.perform(get("/customers/list-customers-filtered-by-name").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.totalElements").value(2))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	@DisplayName("Should List Customers Filtered By Name When Name Contains 'Nome' And Return Status 200.")
	void listCustomersFilteredByNameTest2() throws Exception {
		Customer customer1 = new Customer(null, "nome1", "99863221465", "email1@gmail.com", "81911111111", null);

		Customer customer2 = new Customer(null, "nome2", "81899898000183", "email2@gmail.com", "81922222222", null);

		Customer customer3 = new Customer(null, "nome3", "60996653406", "email3@gmail.com", "81933333333", null);

		customerRepository.save(customer1);

		customerRepository.save(customer2);

		customerRepository.save(customer3);

		mockMvc.perform(get("/customers/list-customers-filtered-by-name").queryParam("name", "Nome")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(3)).andExpect(status().isOk()).andDo(print());
	}
}
