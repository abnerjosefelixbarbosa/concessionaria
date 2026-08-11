package com.concessionaria.backend.controller.vehicle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.repository.ModelRepository;
import com.concessionaria.backend.model.repository.VehicleRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FindVehicleByIdTI {
	@Autowired
	private MockMvc mockMvc;
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
	@DisplayName("Should find vehicle by id and return status 200.")
	void findVehicleByIdTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);
		
		Vehicle vehicle = new Vehicle(null, "1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("10000.00"), model, null);

		vehicle = vehicleRepository.save(vehicle);

		mockMvc.perform(get("/vehicles/find-vehicle-by-id/" + vehicle.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@DisplayName("Should not find vehicle by id when id is not existent and return status 404.")
	void findVehicleByIdTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);
		
		Vehicle vehicle = new Vehicle(null, "1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("10000.00"), model, null);

		vehicle = vehicleRepository.save(vehicle);

		mockMvc.perform(get("/vehicles/find-vehicle-by-id/1" + vehicle.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
	}
}