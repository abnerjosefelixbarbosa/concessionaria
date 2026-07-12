package com.concessionaria.backend.controller.model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class ModelUpdateByIdIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private BrandRepository brandRepository;

	@BeforeEach
	void setUp() {
		modelRepository.deleteAll();
		brandRepository.deleteAll();
	}

	@Test
	void shouldUpdateModelByIdAndReturnStatus200() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("nome2", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldNotUpdateModelByIdWhenNameIsNullAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO(null, "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateModelByIdWhenNameIsEmptyAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateModelByIdWhenNameContains31CharactersAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("nome111111111111111111111111111", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateModelByIdWhenNameIsRepeatedAndReturnStatus400() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("nome1", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void shouldNotUpdateModelByIdWhenBrandNameIsNotExistentAndReturnStatus404() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("nome2", "nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound()).andDo(print());
	}
}
