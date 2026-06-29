package com.concessionaria.backend.controller.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.concessionaria.backend.model.dto.CustomerRequestDTO;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.repository.CustomerRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerUpdateByIdIntegrationTest {
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
	void shouldUpdateCustomerByIdAndReturnStatus200() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenIdIsNonExistentAndReturnStatus404() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/1" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andDo(print());
	}

	@Test
	void shouldNotUpdateCustomerByIdWhenNameIsNullAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO(null, "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotUpdateCustomerByIdWhenNameIsEmptyAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("", "83712704453", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotUpdateCustomerByIdWhenNameIsRepeatedAndReturnStatus400() throws Exception {
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
	void shouldNotUpdateCustomerByIdWhenDocumentIsNullAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", null, "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotUpdateCustomerByIdWhenDocumentIsEmptyAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.document").value("Documento deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotUpdateCustomerByIdWhenDocumentIsInvalidCPFAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704451", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}

	@Test
	void shouldNotUpdateCustomerByIdWhenDocumentIsInvalidCNPJAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "23896519000103", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();
		
		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "29822617000171", "email2@gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Documento deve ser CPF ou CNPJ valido.")).andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenDocumentIsRepeatedAndReturnStatus400() throws Exception {
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
	void shouldNotUpdateCustomerByIdWhenEmailIsNullAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", null, "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenEmailIsEmptyAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenEmailIsInvalidAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email1gmail.com", "81922222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email").value("Email deve ser valido."))
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenEmailIsRepeatedAndReturnStatus400() throws Exception {
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
	void shouldNotUpdateCustomerByIdWhenPhoneIsNullAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenPhoneIsEmptyAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenPhoneContains31CharactersAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "8192222222222222222222222222222");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.phone").value("Telefone deve ter até 30 caracteres."))
				.andDo(print());
	}
	
	@Test
	void shouldNotUpdateCustomerByIdWhenPhoneIsRepeatedAndReturnStatus400() throws Exception {
		Customer customer = new Customer(null, "nome1", "26516743460", "email1@gmail.com", "81911111111", null);

		String id = customerRepository.save(customer).getId();

		CustomerRequestDTO dto = new CustomerRequestDTO("nome2", "83712704453", "email2@gmail.com", "81911111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/customers/update-customer-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome, documento, email ou telefone não deve ser repetido."))
				.andDo(print());
	}
}
