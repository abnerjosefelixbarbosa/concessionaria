package com.concessionaria.backend.controller.sale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.concessionaria.backend.model.dto.ItemRequestDTO;
import com.concessionaria.backend.model.dto.SaleRequestDTO;
import com.concessionaria.backend.model.entity.Brand;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.entity.Employee;
import com.concessionaria.backend.model.entity.Model;
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

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RegisterSaleTI {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
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
	@DisplayName("Should register sale and return status 201.")
	void registerSaleTest1() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isCreated()).andDo(print());
	}

	@Test
	@DisplayName("Should not register sale when payment type is null and return status 400.")
	void registerSaleTest2() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));

		SaleRequestDTO dto = new SaleRequestDTO(null, "1111111111", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.paymentType").value("Tipo do pagamento deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when matriculation is null and return status 400.")
	void registerSaleTest3() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, null, "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.matriculation").value("Matricula do funcionário deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when matriculation is empty and return status 400.")
	void registerSaleTest4() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.matriculation").value("Matricula do funcionário deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when document is null and return status 400.")
	void registerSaleTest5() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", null, requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.document").value("Documento do cliente deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when document is empty and return status 400.")
	void registerSaleTest6() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", "", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.document").value("Documento do cliente deve ser obrigatório."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when item list is null and return status 400.")
	void registerSaleTest7() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = null;

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.items").value("Items deve ser obrigatorios."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when matriculation not is existent and return status 404.")
	void registerSaleTest8() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));;

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111112", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isNotFound(),
						jsonPath("$.message").value("Matricula do funcionário deve ser existente."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when document not is existent and return status 404.")
	void registerSaleTest9() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));;

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", "70230924475", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isNotFound(),
						jsonPath("$.message").value("Documento deve ser existente."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when plate not is existent and return status 400.")
	void registerSaleTest10() throws Exception {
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

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-111"), new ItemRequestDTO("AAA-2222"));;

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isNotFound(),
						jsonPath("$.message").value("Lista de itens deve ter placa de veiculo existente."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when employee type is not SALLER  and return status 400.")
	void registerSaleTest11() throws Exception {
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

		Employee employee1 = new Employee(null, "nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1995), "46358981480", new BigDecimal("2000.00"), 10, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);
		
		Employee employee2 = new Employee(null, "nome2", "1111111112", "email2@gmail.com", "81911111112",
				LocalDate.now().withYear(1995), "47150987419", new BigDecimal("3000.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER, null);

		employeeRepository.save(employee1);
		
		employeeRepository.save(employee2);

		Customer customer = new Customer(null, "nome1", "70230924476", "email1@gmail.com", "81911111111",
				CustomerType.PF, null);

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));;

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111112", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.message").value("Matricula do funcionário deve ser de um vendedor."))
				.andDo(print());
	}
	
	@Test
	@DisplayName("Should not register sale when vehicle status is SOLD and return status 400.")
	void registerSaleTest12() throws Exception {
		Brand brand = new Brand(null, "nome1", null);

		brand = brandRepository.save(brand);

		Model model = new Model(null, "nome1", brand, null);

		model = modelRepository.save(model);

		Vehicle vehicle1 = new Vehicle(null, "AAA-1111", TransmissionType.AUTOMATIC, VehicleStatus.FOR_SALE, "cor1",
				new BigDecimal("3000.00"), model, null);

		Vehicle vehicle2 = new Vehicle(null, "AAA-2222", TransmissionType.AUTOMATIC, VehicleStatus.SOLD, "cor1",
				new BigDecimal("7000.00"), model, null);

		vehicle1 = vehicleRepository.save(vehicle1);

		vehicle2 = vehicleRepository.save(vehicle2);

		Employee employee1 = new Employee(null, "nome1", "1111111111", "email1@gmail.com", "81911111111",
				LocalDate.now().withYear(1995), "46358981480", new BigDecimal("2000.00"), 10, EmployeeStatus.ACTIVE,
				EmployeeType.SALLER, null);
		
		Employee employee2 = new Employee(null, "nome2", "1111111112", "email2@gmail.com", "81911111112",
				LocalDate.now().withYear(1995), "47150987419", new BigDecimal("3000.00"), null, EmployeeStatus.ACTIVE,
				EmployeeType.MANAGER, null);

		employeeRepository.save(employee1);
		
		employeeRepository.save(employee2);

		Customer customer = new Customer(null, "nome1", "70230924476", "email1@gmail.com", "81911111111",
				CustomerType.PF, null);

		customerRepository.save(customer);

		List<ItemRequestDTO> requestDTOs = List.of(new ItemRequestDTO("AAA-1111"), new ItemRequestDTO("AAA-2222"));;

		SaleRequestDTO dto = new SaleRequestDTO(PaymentType.CASH, "1111111111", "70230924476", requestDTOs);

		String json = objectMapper.writeValueAsString(dto);

		mockMvc.perform(
				post("/sales").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.message").value("Lista de itens deve ter veiculos a venda."))
				.andDo(print());
	}
}
