package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.VehicleRequestDTO;
import com.concessionaria.backend.model.dto.VehicleResponseDTO;
import com.concessionaria.backend.model.entity.Model;
import com.concessionaria.backend.model.entity.Vehicle;

public class VehicleMapper {
	public static Vehicle toVehicle(VehicleRequestDTO dto) {
		Model model = new Model(null, dto.modelName(), null, null);

		return new Vehicle(null, dto.plate(), dto.transmissionType(), dto.vehicleStatus(), dto.color(), dto.price(),
				model, null);
	}

	public static VehicleResponseDTO toBrandResponseDTO(Vehicle vehicle) {
		return new VehicleResponseDTO(vehicle.getId(), vehicle.getPlate(), vehicle.getTransmissionType(),
				vehicle.getVehicleStatus(), vehicle.getColor(), vehicle.getPrice(), vehicle.getModel().getName());
	}
}