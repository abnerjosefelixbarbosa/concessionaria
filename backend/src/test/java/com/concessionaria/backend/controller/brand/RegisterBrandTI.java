package com.concessionaria.backend.controller.brand;

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

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.repository.BrandRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RegisterBrandTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private BrandRepository brandRepository;
	
	@BeforeEach
	void setUp() {
		brandRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		brandRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Should register brand and return status 201.")
	void registerBrandTest1() throws Exception {
		BrandRequestDTO dto = new BrandRequestDTO("nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/brands/register-brand").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register brand when name is null and return status 400.")
	void registerBrandTest2() throws Exception {
		BrandRequestDTO dto = new BrandRequestDTO(null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/brands/register-brand").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register brand when name is empty and return status 400.")
	void registerBrandTest3() throws Exception {
		BrandRequestDTO dto = new BrandRequestDTO("");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/brands/register-brand").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register brand when name contains 31 characters and return status 400.")
	void registerBrandTest4() throws Exception {
		BrandRequestDTO dto = new BrandRequestDTO("nome111111111111111111111111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/brands/register-brand").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ter até 30 caracteres.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register brand when name is repeated and return status 400.")
	void registerBrandTest5() throws Exception {
		Brand brand = new Brand(null, "nome1", null);
		
		brandRepository.save(brand);
		
		BrandRequestDTO dto = new BrandRequestDTO("nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/brands/register-brand").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome deve não deve ser repetido.")).andDo(print());
	}
}