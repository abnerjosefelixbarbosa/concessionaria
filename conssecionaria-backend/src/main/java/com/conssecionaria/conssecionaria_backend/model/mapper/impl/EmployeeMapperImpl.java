package com.conssecionaria.conssecionaria_backend.model.mapper.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.Employee;
import com.conssecionaria.conssecionaria_backend.model.mapper.EmployeeMapper;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
	public Employee toEmployee(EmployeeRequestDTO dto) {
		Employee employee = new Employee();
		
		BeanUtils.copyProperties(dto, employee);
		
		return employee;
	}

	public EmployeeResponseDTO toEmployeeResponseDTO(Employee employee) {
		EmployeeResponseDTO dto = new EmployeeResponseDTO();
		
		BeanUtils.copyProperties(employee, dto);

		return dto;
	}
}