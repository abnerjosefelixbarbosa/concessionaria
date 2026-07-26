package com.concessionaria.backend.controller.brand;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.concessionaria.backend.model.dto.BrandRequestDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.repository.BrandRepository;

import tools.jackson.databind.ObjectMapper;

class UpdateBrandByIdTI {
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
	@DisplayName("Should update brand by id and return status 200.")
	void updateBrandByIdTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not update brand by id when id is not existent and return status 404.")
	void updateBrandByIdTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/1" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not update brand by id when name is null and return status 400.")
	void updateBrandByIdTest3() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO(null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not update brand by id when name is empty and return status 400.")
	void updateBrandByIdTest4() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not update brand by id when name contains 31 characters and return status 400.")
	void updateBrandByIdTest5() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("nome111111111111111111111111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ter até 30 caracteres.")).andDo(print());
	}
	
	@Test
	@DisplayName("Should not update brand by id when name is repeated and return status 400.")
	void updateBrandByIdTest6() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		BrandRequestDTO dto = new BrandRequestDTO("nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/brands/update-brand-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome deve não deve ser repetido.")).andDo(print());
	}
}