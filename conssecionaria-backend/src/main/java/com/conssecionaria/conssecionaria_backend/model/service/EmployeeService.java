package com.conssecionaria.conssecionaria_backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;

public interface EmployeeService {
	EmployeeResponseDTO registerEmployee(EmployeeRequestDTO dto);

	EmployeeResponseDTO updateEmployeeById(String id, EmployeeRequestDTO dto);

	EmployeeResponseDTO findEmployeeById(String id);

	Page<EmployeeResponseDTO> listEmployees(String name, EmployeeStatus employeeStatus,
			EmployeeType employeeType, Pageable pageable);
}