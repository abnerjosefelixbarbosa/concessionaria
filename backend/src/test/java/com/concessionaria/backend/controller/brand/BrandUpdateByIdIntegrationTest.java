package com.concessionaria.backend.controller.brand;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class BrandUpdateByIdIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private BrandRepository brandRepository;

	@BeforeEach
	void setUp() throws Exception {
		brandRepository.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
		brandRepository.deleteAll();
	}

	@Test
	void shouldUpdateBrandByIdAndReturnStatus200() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldNotUpdateBrandByIdWhenIdIsNonExistentAndReturnStatus404() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/1" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andDo(print());
	}
}
