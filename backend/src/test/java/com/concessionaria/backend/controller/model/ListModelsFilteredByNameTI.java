package com.concessionaria.backend.controller.model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

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
class ListModelsFilteredByNameTI {
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
	@DisplayName("Should list models filtered by name and return status 200.")
	void listModelsFilteredByNameTest1() throws Exception {
		Brand brand1 = new Brand(null, "nome1", null);

		Brand brand2 = new Brand(null, "nome2", null);

		List<Brand> brands = brandRepository.saveAll(List.of(brand1, brand2));

		Model model1 = new Model(null, "nome1", brands.get(0), null);

		Model model2 = new Model(null, "nome2", brands.get(1), null);

		modelRepository.saveAll(List.of(model1, model2));

		mockMvc.perform(get("/models/list-models-filtered-by-name").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), jsonPath("$.numberOfElements").value("2")).andDo(print());
	}
	
	@Test
	@DisplayName("Should list models filtered by name when name is 'Nome' and return status 200.")
	void listModelsFilteredByNameTest2() throws Exception {
		Brand brand1 = new Brand(null, "nome1", null);

		Brand brand2 = new Brand(null, "nome2", null);

		List<Brand> brands = brandRepository.saveAll(List.of(brand1, brand2));

		Model model1 = new Model(null, "nome1", brands.get(0), null);

		Model model2 = new Model(null, "nome2", brands.get(1), null);

		modelRepository.saveAll(List.of(model1, model2));

		mockMvc.perform(get("/models/list-models-filtered-by-name").queryParam("name", "Nome").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), jsonPath("$.numberOfElements").value("2")).andDo(print());
	}
}