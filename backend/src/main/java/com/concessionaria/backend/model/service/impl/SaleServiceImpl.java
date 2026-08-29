package com.concessionaria.backend.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.SaleRequestDTO;
import com.concessionaria.backend.model.dto.SaleResponseDTO;
import com.concessionaria.backend.model.entity.Customer;
import com.concessionaria.backend.model.entity.Employee;
import com.concessionaria.backend.model.entity.Item;
import com.concessionaria.backend.model.entity.Sale;
import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.entity.enums.EmployeeType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.SaleMapper;
import com.concessionaria.backend.model.repository.SaleRepository;
import com.concessionaria.backend.model.service.CustomerService;
import com.concessionaria.backend.model.service.EmployeeService;
import com.concessionaria.backend.model.service.SaleService;
import com.concessionaria.backend.model.service.VehicleService;

import jakarta.transaction.Transactional;

@Service
public class SaleServiceImpl implements SaleService {
	private final SaleRepository saleRepository;
	private final CustomerService customerService;
	private final EmployeeService employeeService;
	private final VehicleService vehicleService;

	public SaleServiceImpl(SaleRepository saleRepository, CustomerService customerService,
			EmployeeService employeeService, VehicleService vehicleService) {
		this.saleRepository = saleRepository;
		this.customerService = customerService;
		this.employeeService = employeeService;
		this.vehicleService = vehicleService;
	}

	@Transactional
	public SaleResponseDTO registerSale(SaleRequestDTO dto) {
		Sale sale = SaleMapper.toSale(dto);

		Customer customer = customerService.findCustomerByDocument(dto.document());

		Employee employee = employeeService.findEmployeeByMatriculation(dto.matriculation());

		sale.setCustomer(customer);

		sale.setEmployee(employee);

		List<Item> items = mapItems(dto, sale);

		sale.setItems(items);

		validadeSale(sale);

		updateVehicle(sale);

		Sale saleSaved = saleRepository.save(sale);

		return SaleMapper.toSaleResponseDTO(saleSaved);
	}

	public void deleterSaleById(String id) {
		Sale sale = saleRepository.findById(id).orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		saleRepository.delete(sale);
	}

	private List<Item> mapItems(SaleRequestDTO dto, Sale sale) {
		return dto.items().stream().map(item -> {
			Vehicle vehicle = vehicleService.findVehicleByPlate(item.plate());

			Item newItem = new Item();
			newItem.setSale(sale);
			newItem.setVehicle(vehicle);

			BigDecimal total = sale.getTotalValue().add(vehicle.getPrice());

			sale.setTotalValue(total);

			newItem.setSale(sale);

			return newItem;
		}).toList();
	}

	private void updateVehicle(Sale sale) {
		sale.getItems().stream().forEach((item) -> {
			item.getVehicle().setVehicleStatus(VehicleStatus.SOLD);

			vehicleService.updateVehicleByPlate(item.getVehicle().getPlate(), item.getVehicle());
		});
	}

	private void validadeSale(Sale sale) {
		if (sale.getEmployee().getEmployeeType() != EmployeeType.SALLER) {
			throw new ApplicationException("Matricula do funcionário deve ser de um vendedor.");
		}

		sale.getItems().stream().forEach((item) -> {
			if (item.getVehicle().getVehicleStatus() == VehicleStatus.SOLD) {
				throw new ApplicationException("Lista de itens deve ter veiculos a venda.");
			}
		});
	}
}
