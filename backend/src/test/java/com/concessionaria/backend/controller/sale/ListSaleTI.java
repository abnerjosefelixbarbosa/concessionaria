package com.concessionaria.backend.controller.sale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.entity.Employee;
import com.concessionaria.backend.model.entity.Item;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.entity.Sale;
import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.entity.enums.CustomerType;
import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;
import com.concessionaria.backend.model.entity.enums.PaymentType;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;
import com.concessionaria.backend.model.repository.BrandRepository;
import com.concessionaria.backend.model.repository.CustomerRepository;
import com.concessionaria.backend.model.repository.EmployeeRepository;
import com.concessionaria.backend.model.repository.ItemRepository;
import com.concessionaria.backend.model.repository.ModelRepository;
import com.concessionaria.backend.model.repository.SaleRepository;
import com.concessionaria.backend.model.repository.VehicleRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ListSaleTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@BeforeEach
	void setUp() {
		itemRepository.deleteAll();
		vehicleRepository.deleteAll();
		modelRepository.deleteAll();
		brandRepository.deleteAll();
		saleRepository.deleteAll();
		customerRepository.deleteAll();
		employeeRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		itemRepository.deleteAll();
		vehicleRepository.deleteAll();
		modelRepository.deleteAll();
		brandRepository.deleteAll();
		saleRepository.deleteAll();
		customerRepository.deleteAll();
		employeeRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Should list sale and return status 200.")
	void listSaleTest1() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		Vehicle vehicle1 = new Vehicle(null, "AAA-1111", TransmissionType.AUTOMATIC, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("3000.00"), model, null);

		Vehicle vehicle2 = new Vehicle(null, "AAA-2222", TransmissionType.AUTOMATIC, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("7000.00"), model, null);

		vehicle1 = vehicleRepository.save(vehicle1);

		vehicle2 = vehicleRepository.save(vehicle2);

		Employee employee = new Employee(null, "nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1995), "46358981480", new BigDecimal("2000.00"), 10, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		employee = employeeRepository.save(employee);

		Customer customer = new Customer(null, "nome1", "70230924476", "email1@gmail.com", "81911111111",
				CustomerType.PF, null);

		customer = customerRepository.save(customer);
		
		Sale sale = new Sale(null, LocalDate.now(), PaymentType.CASH, new BigDecimal("100000"), employee, customer, null);
		
		sale = saleRepository.save(sale);
		
		List<Item> items = List.of(new Item(null, sale, vehicle1), new Item(null, sale, vehicle2));

		itemRepository.saveAll(items);

		mockMvc.perform(
				get(String.format("/sales")).contentType(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk()).andDo(print());
	}
	
	@Test
	@DisplayName("Should list sale when paymentType is 'CASH' and return status 200.")
	void listSaleTest2() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		Vehicle vehicle1 = new Vehicle(null, "AAA-1111", TransmissionType.AUTOMATIC, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("3000.00"), model, null);

		Vehicle vehicle2 = new Vehicle(null, "AAA-2222", TransmissionType.AUTOMATIC, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("7000.00"), model, null);
		
		Vehicle vehicle3 = new Vehicle(null, "AAA-3333", TransmissionType.AUTOMATIC, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("7000.00"), model, null);

		vehicle1 = vehicleRepository.save(vehicle1);

		vehicle2 = vehicleRepository.save(vehicle2);
		
		vehicle3 = vehicleRepository.save(vehicle3);

		Employee employee = new Employee(null, "nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1995), "46358981480", new BigDecimal("2000.00"), 10, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);

		employee = employeeRepository.save(employee);

		Customer customer = new Customer(null, "nome1", "70230924476", "email1@gmail.com", "81911111111",
				CustomerType.PF, null);

		customer = customerRepository.save(customer);
		
		Sale sale = new Sale(null, LocalDate.now(), PaymentType.CASH, new BigDecimal("10000.00"), employee, customer, null);
		
		Sale sale1 = new Sale(null, LocalDate.now(), PaymentType.CREDIT_CARD, new BigDecimal("7000.00"), employee, customer, null);
		
		sale = saleRepository.save(sale);
		
		sale1 = saleRepository.save(sale1);
		
		List<Item> items = List.of(new Item(null, sale, vehicle1), new Item(null, sale, vehicle2));
		
		List<Item> items1 = List.of(new Item(null, sale1, vehicle3));

		itemRepository.saveAll(items);
		
		itemRepository.saveAll(items1);

		mockMvc.perform(
				get(String.format("/sales")).queryParam("paymentType", sale.getPaymentType().name()).contentType(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), jsonPath("$.numberOfElements").value(1)).andDo(print());
	}
}