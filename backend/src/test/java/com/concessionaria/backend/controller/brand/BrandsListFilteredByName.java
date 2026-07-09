package com.concessionaria.backend.controller.brand;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.repository.BrandRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BrandsListFilteredByName {
	@Autowired
	private MockMvc mockMvc;
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
	void shouldListBrandsFilteredByNameAndReturnStatus200() throws Exception {
		Brand brand1 = new Brand(null, "nome1", null);

		Brand brand2 = new Brand(null, "nome2", null);

		brandRepository.save(brand1);

		brandRepository.save(brand2);

		mockMvc.perform(get("/brands/list-brands-filtered-by-name").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.totalElements").value(2))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldListBrandsFilteredByNameWhenNameIsNameAndReturnStatus200() throws Exception {
		Brand brand1 = new Brand(null, "nome1", null);

		Brand brand2 = new Brand(null, "nome2", null);

		brandRepository.save(brand1);

		brandRepository.save(brand2);

		mockMvc.perform(get("/brands/list-brands-filtered-by-name").queryParam("name", "Nome")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalElements").value(2)).andExpect(status().isOk()).andDo(print());
	}
}
