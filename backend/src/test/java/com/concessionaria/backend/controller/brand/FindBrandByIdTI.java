package com.concessionaria.backend.controller.brand;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.repository.BrandRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FindBrandByIdTI {
	@Autowired
	private MockMvc mockMvc;
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
	@DisplayName("Should find brand by id and return status 200.")
	void findBrandByIdTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		mockMvc.perform(get("/brands/find-brand-by-id/" + id)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@DisplayName("Should not find brand by id when id is not existent and return status 404.")
	void findBrandByIdTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		String id = brandRepository.save(brand).getId();

		mockMvc.perform(get("/brands/find-brand-by-id/1" + id)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Id deve ser existente.")).andDo(print());
	}
}