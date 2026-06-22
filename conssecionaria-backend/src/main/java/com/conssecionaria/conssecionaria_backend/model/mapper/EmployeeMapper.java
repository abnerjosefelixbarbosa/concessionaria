package com.conssecionaria.conssecionaria_backend.model.mapper;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.Employee;

public interface EmployeeMapper {
	Employee toEmployee(EmployeeRequestDTO dto);

	EmployeeResponseDTO toEmployeeResponseDTO(Employee employee);
}