package com.concessionaria.backend.controller.vehicle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.repository.ModelRepository;
import com.concessionaria.backend.model.repository.VehicleRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RegisterVehicleTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private BrandRepository brandRepository;

	@BeforeEach
	void setUp() throws Exception {
		vehicleRepository.deleteAll();
		modelRepository.deleteAll();
		brandRepository.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
		vehicleRepository.deleteAll();
		modelRepository.deleteAll();
		brandRepository.deleteAll();
	}

	@Test
	@DisplayName("Should register vehicle and return status 201.")
	void registerVehicleTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when plate is null and return status 400.")
	void registerVehicleTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO(null, TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when plate is empty and return status 400.")
	void registerVehicleTest3() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when plate contains more than 10 characters and return status 400.")
	void registerVehicleTest4() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when transmission type is null and return status 400.")
	void registerVehicleTest5() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", null, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when vehicle status is null and return status 400.")
	void registerVehicleTest6() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, null,
				"cor1", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when color is null and return status 400.")
	void registerVehicleTest7() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				null, new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when color is empty and return status 400.")
	void registerVehicleTest8() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when color contains more than 30 characters and return status 400.")
	void registerVehicleTest9() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor11111111111111111111111111111111111111111111111111", new BigDecimal("10000.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when price is null and return status 400.")
	void registerVehicleTest10() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", null, "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when model name is null and return status 400.")
	void registerVehicleTest11() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), null);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when model name is empty and return status 400.")
	void registerVehicleTest12() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when model name contains more than 30 characters and return status 400.")
	void registerVehicleTest13() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome111111111111111111111111111111111111111111111");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when plate is not existent and return status 400.")
	void registerVehicleTest14() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("10000.00"), "nome2");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when price not contains 2 digits and return status 400.")
	void registerVehicleTest15() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("1000000"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not register vehicle when price not contains 2 digits and return status 400.")
	void registerVehicleTest16() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		VehicleRequestDTO dto = new VehicleRequestDTO("1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE,
				"cor1", new BigDecimal("0.00"), "nome1");

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/vehicles/register-vehicle").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andDo(print());
	}
}