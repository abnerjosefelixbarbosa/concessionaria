package com.conssecionaria.conssecionaria_backend.model.service;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;

public interface EmployeeService {
	public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO dto);

	public EmployeeResponseDTO updateEmployeeById(String id, EmployeeRequestDTO dto);
}