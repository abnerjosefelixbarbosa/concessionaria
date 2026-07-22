package com.concessionaria.backend.controller;

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

import com.concessionaria.backend.model.dto.ModelRequestDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.repository.ModelRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ModelTI {
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

	@AfterEach
	void tearDown() {
		modelRepository.deleteAll();
		brandRepository.deleteAll();
	}

	// register model

	@Test
	@DisplayName("Should Register Model And Return Status 201.")
	void registerModelTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("nome1", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Model When Name Is Null And Return Status 400.")
	void registerModelTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO(null, "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Model When Name Is Empty And Return Status 400.")
	void registerModelTest3() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ser obrigatório.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Model When Name Is Contains 31 Characters And Return Status 400.")
	void registerModelTest4() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("nome111111111111111111111111111", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Nome deve ter até 30 caracteres.")).andDo(print());
	}

	@Test
	@DisplayName("Should Not Register Model When Name Is Repeated And Return Status 400.")
	void registerModelTest5() throws Exception {
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
	@DisplayName("Should Not Register Model When Brand Name Is Not Existent And Return Status 404.")
	void registerModelTest6() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		ModelRequestDTO dto = new ModelRequestDTO("nome1", "nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/models/register-model").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Nome da marca deve ser existente.")).andDo(print());
	}

	// update model by id

	@Test
	@DisplayName("Should Update Model By Id And Return Status 200.")
	void updateModelByIdTest1() throws Exception {
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
	@DisplayName("Should Not Update Model By Id When Name Is Null And Return Status 400.")
	void updateModelByIdTest2() throws Exception {
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
	@DisplayName("Should Not Update Model By Id When Name Is Empty And Return Status 400.")
	void UpdateModelByIdTest3() throws Exception {
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
	@DisplayName("Should Not Update Model By Id When Name Contains 31 Characters And Return Status 400.")
	void updateModelByIdTest4() throws Exception {
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
	@DisplayName("Should Not Update Model By Id When Name Is Repeated And Return Status 400.")
	void updateModelByIdTest5() throws Exception {
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
	@DisplayName("Should Not Update Model By Id When Brand Name Is Not Existent And Return Status 404.")
	void updateModelByIdTest6() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("nome2", "nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	@DisplayName("Should Update Model By Id When Id Is Not Existent And Return Status 404.")
	void updateModelByIdTest7() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		String id = modelRepository.save(model).getId();

		ModelRequestDTO dto = new ModelRequestDTO("nome2", "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(put("/models/update-model-by-id/1" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(jsonPath("$.message").value("Id deve ser existente."), status().isNotFound())
				.andDo(print());
	}
}
