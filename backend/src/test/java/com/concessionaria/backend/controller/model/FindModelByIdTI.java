package com.concessionaria.backend.controller.model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.repository.ModelRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FindModelByIdTI {
	@Autowired
	private MockMvc mockMvc;
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

	@Test
	@DisplayName("Should find model by id and return status 200.")
	void findModelByIdTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		Brand brandSaved = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brandSaved, null);

		Model modelSaved = modelRepository.save(model);

		mockMvc.perform(get("/models/find-model-by-id/" + modelSaved.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@DisplayName("Should find model by id when id is not existent and return status 404.")
	void findModelByIdTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		Brand brandSaved = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brandSaved, null);

		Model modelSaved = modelRepository.save(model);

		mockMvc.perform(get("/models/find-model-by-id/1" + modelSaved.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isNotFound(), jsonPath("$.message").value("Id deve ser existente."))
				.andDo(print());
	}
}