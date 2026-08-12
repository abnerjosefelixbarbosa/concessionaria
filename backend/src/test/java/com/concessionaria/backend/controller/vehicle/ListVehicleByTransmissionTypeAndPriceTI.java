package com.concessionaria.backend.controller.vehicle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
class ListVehicleByTransmissionTypeAndPriceTI {
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
	@DisplayName("Should list vehicle by transmission type and price and return status 200.")
	void listVehicleByTransmissionTypeAndPriceTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);
		
		Vehicle vehicle1 = new Vehicle(null, "1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("10000.00"), model, null);
		
		Vehicle vehicle2 = new Vehicle(null, "2222-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor2",
				new BigDecimal("5000.00"), model, null);
		
		Vehicle vehicle3 = new Vehicle(null, "3333-AAA", TransmissionType.AUTOMATIC, VehicleStatus.SOLD, "cor3",
				new BigDecimal("8000.00"), model, null);

		vehicleRepository.save(vehicle1);
		
		vehicleRepository.save(vehicle2);
		
		vehicleRepository.save(vehicle3);
		
		mockMvc.perform(get("/vehicles/list-vehicle-by-transmission-type-and-price").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpectAll(
						status().isOk(),
						jsonPath("$.numberOfElements").value("3")
				).andDo(print());
	}
	
	@Test
	@DisplayName("Should list vehicle by transmission type and price when transmission type contains 'MANUAL' and return status 200.")
	void listVehicleByTransmissionTypeAndPriceTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);
		
		Vehicle vehicle1 = new Vehicle(null, "1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("10000.00"), model, null);
		
		Vehicle vehicle2 = new Vehicle(null, "2222-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor2",
				new BigDecimal("5000.00"), model, null);
		
		Vehicle vehicle3 = new Vehicle(null, "3333-AAA", TransmissionType.AUTOMATIC, VehicleStatus.SOLD, "cor3",
				new BigDecimal("8000.00"), model, null);

		vehicleRepository.save(vehicle1);
		
		vehicleRepository.save(vehicle2);
		
		vehicleRepository.save(vehicle3);
		
		mockMvc.perform(get("/vehicles/list-vehicle-by-transmission-type-and-price").queryParam("transmissionType", TransmissionType.MANUAL.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpectAll(
						status().isOk(),
						jsonPath("$.numberOfElements").value("2")
				).andDo(print());
	}
	
	@Test
	@DisplayName("Should list vehicle by transmission type and price when price is '8000' return status 200.")
	void listVehicleByTransmissionTypeAndPriceTest3() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);
		
		Vehicle vehicle1 = new Vehicle(null, "1111-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("10000.00"), model, null);
		
		Vehicle vehicle2 = new Vehicle(null, "2222-AAA", TransmissionType.MANUAL, VehicleStatus.FOR_SALE, "cor2",
				new BigDecimal("5000.00"), model, null);
		
		Vehicle vehicle3 = new Vehicle(null, "3333-AAA", TransmissionType.AUTOMATIC, VehicleStatus.SOLD, "cor3",
				new BigDecimal("8000.00"), model, null);

		vehicleRepository.save(vehicle1);
		
		vehicleRepository.save(vehicle2);
		
		vehicleRepository.save(vehicle3);
		
		mockMvc.perform(get("/vehicles/list-vehicle-by-transmission-type-and-price").queryParam("price", "8000").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpectAll(
						status().isOk(),
						jsonPath("$.numberOfElements").value("2")
				).andDo(print());
	}
}
