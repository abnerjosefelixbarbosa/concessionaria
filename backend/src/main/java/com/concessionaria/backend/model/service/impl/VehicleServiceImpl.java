package com.concessionaria.backend.model.service.impl;

import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.dto.VehicleResponseDTO;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.entity.Vehicle;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.VehicleMapper;
import com.concessionaria.backend.model.repository.VehicleRepository;
import com.concessionaria.backend.model.service.ModelService;
import com.concessionaria.backend.model.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {
	private final VehicleRepository vehicleRepository;
	private final ModelService modelService;

	public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelService modelService) {
		this.vehicleRepository = vehicleRepository;
		this.modelService = modelService;
	}

	public VehicleResponseDTO registerVehicle(VehicleRequestDTO dto) {
		Vehicle vehicle = VehicleMapper.toVehicle(dto);

		validateVehicle(vehicle);

		Model modelFound = modelService.findModelByName(vehicle.getModel().getName());

		vehicle.setModel(modelFound);

		Vehicle vehicleSaved = vehicleRepository.save(vehicle);

		return VehicleMapper.toBrandResponseDTO(vehicleSaved);
	}

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