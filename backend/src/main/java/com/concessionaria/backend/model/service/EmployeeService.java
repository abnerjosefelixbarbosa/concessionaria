package com.concessionaria.backend.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concessionaria.backend.model.dto.EmployeeRequestDTO;
import com.concessionaria.backend.model.dto.EmployeeResponseDTO;
import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;

public interface EmployeeService {
	EmployeeResponseDTO registerEmployee(EmployeeRequestDTO dto);

	EmployeeResponseDTO updateEmployeeById(String id, EmployeeRequestDTO dto);

	EmployeeResponseDTO findEmployeeById(String id);

	Page<EmployeeResponseDTO> listEmployeesByNameAndEmployeeStatusAndEmployeeType(String name,
			EmployeeStatus employeeStatus, EmployeeType employeeType, Pageable pageable);
}
