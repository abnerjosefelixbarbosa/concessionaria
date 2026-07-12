package com.concessionaria.backend.controller.model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.repository.ModelRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ModelRegisterIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private BrandRepository brandRepository;

	@BeforeEach
	void setUp() throws Exception {
		modelRepository.deleteAll();
		brandRepository.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
		modelRepository.deleteAll();
		brandRepository.deleteAll();
	}

	@Test
	void shouldRegisterModelAndReturnStatus201() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("nome1", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void shouldNotRegisterModelWhenNameIsNullAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO(null, "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	void shouldNotRegisterModelWhenNameIsEmptyAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}
	
	@Test
	void shouldNotRegisterModelWhenNameIsContains31CharactersAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("nome111111111111111111111111111", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ter até 30 caracteres.")).andDo(print());
	}

	@Test
	void shouldNotRegisterModelWhenNameIsRepeatedAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		modelRepository.save(model);

		ModelRequestDTO dto = new ModelRequestDTO("nome1", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Nome não deve ser repetido.")).andDo(print());
	}
	
	@Test
	void shouldNotRegisterModelWhenBrandNameIsNotExistentAndReturnStatus404() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("nome1", "nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Nome da marca deve ser existente.")).andDo(print());
	}
}
