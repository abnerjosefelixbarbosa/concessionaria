package com.concessionaria.backend.model.mapper;

import com.concessionaria.backend.model.dto.EmployeeRequestDTO;
import com.concessionaria.backend.model.dto.EmployeeResponseDTO;
import com.concessionaria.backend.model.entity.Employee;

public class EmployeeMapper {
	public static Employee toEmployee(EmployeeRequestDTO dto) {
		Employee employee = new Employee(null, dto.name(), dto.matriculation(), dto.email(), dto.phone(),
				dto.birthDate(), dto.cpf(), dto.salary(), dto.commission(), dto.employeeStatus(), dto.employeeType(),
				null);

		return employee;
	}

	public static EmployeeResponseDTO toEmployeeResponseDTO(Employee employee) {
		EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(employee.getId(), employee.getName(),
				employee.getMatriculation(), employee.getEmail(), employee.getPhone(), employee.getBirthDate(),
				employee.getCpf(), employee.getSalary(), employee.getCommission(), employee.getEmployeeStatus(),
				employee.getEmployeeType());

		return employeeResponseDTO;
	}
}