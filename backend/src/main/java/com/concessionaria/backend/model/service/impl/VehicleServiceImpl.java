package com.concessionaria.backend.model.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.dto.VehicleResponseDTO;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.VehicleMapper;
import com.concessionaria.backend.model.repository.VehicleRepository;
import com.concessionaria.backend.model.service.ModelService;
import com.concessionaria.backend.model.service.VehicleService;

import jakarta.transaction.Transactional;

@Service
public class VehicleServiceImpl implements VehicleService {
	private final VehicleRepository vehicleRepository;
	private final ModelService modelService;

	public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelService modelService) {
		this.vehicleRepository = vehicleRepository;
		this.modelService = modelService;
	}

	@Transactional
	public VehicleResponseDTO registerVehicle(VehicleRequestDTO dto) {
		Vehicle vehicle = VehicleMapper.toVehicle(dto);

		validateVehicle(vehicle);

		Model modelFound = modelService.findModelByName(vehicle.getModel().getName());

		vehicle.setModel(modelFound);

		Vehicle vehicleSaved = vehicleRepository.save(vehicle);

		return VehicleMapper.toBrandResponseDTO(vehicleSaved);
	}

	@Transactional
	public VehicleResponseDTO updateVehicleById(String id, VehicleRequestDTO dto) {
		Vehicle vehicle = VehicleMapper.toVehicle(dto);

		validateVehicle(vehicle);

		Model modelFound = modelService.findModelByName(vehicle.getModel().getName());

		vehicle.setModel(modelFound);

		Vehicle vehicleFound = vehicleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		Vehicle vehicleSaved = vehicleRepository.save(vehicleFound);

		return VehicleMapper.toBrandResponseDTO(vehicleSaved);
	}

	public VehicleResponseDTO findVehicleById(String id) {
		Vehicle vehicleFound = vehicleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		return VehicleMapper.toBrandResponseDTO(vehicleFound);
	}
	
	public Vehicle findVehicleByPlate(String plate) {
		Vehicle vehicle = vehicleRepository.findByPlate(plate)
				.orElseThrow(() -> new NotFoundException("Lista de itens deve ter placa de veiculo existente."));
		
		return vehicle;
	}

	public Page<VehicleResponseDTO> listVehicles(TransmissionType transmissionType, BigDecimal price,
			VehicleStatus vehicleStatus, String color, String plate, Pageable pageable) {
		Page<Vehicle> page = vehicleRepository.listVehicles(transmissionType, price, vehicleStatus, color, plate,
				pageable);

		return page.map(VehicleMapper::toBrandResponseDTO);
	}
	
	@Transactional
	public void updateVehicleByPlate(String plate, Vehicle vehicle) {
		Vehicle vehicleFound = findVehicleByPlate(plate);
		
		BeanUtils.copyProperties(vehicle, vehicleFound, "id");
		
		vehicleRepository.save(vehicleFound);
	}

	private void validateVehicle(Vehicle vehicle) {
		if (vehicleRepository.existsByPlate(vehicle.getPlate())) {
			throw new ApplicationException("Placa não deve ser repetida.");
		}

		if (vehicle.getPrice().scale() != 2) {
			throw new ApplicationException("Preço não deve ter dois dígitos.");
		}

		if (vehicle.getPrice().toString().equals("0.00")) {
			throw new ApplicationException("Preço não deve ser 0.00.");
		}
	}
}