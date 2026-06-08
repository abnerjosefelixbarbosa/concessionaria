package com.conssecionaria.conssecionaria_backend.model.service.impl;

import org.springframework.stereotype.Service;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.Employee;
import com.conssecionaria.conssecionaria_backend.model.exception.ApplicationException;
import com.conssecionaria.conssecionaria_backend.model.mapper.EmployeeMapper;
import com.conssecionaria.conssecionaria_backend.model.repository.EmployeeRepository;
import com.conssecionaria.conssecionaria_backend.model.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
	}

	public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO dto) {
		Employee employee = employeeMapper.toEmployee(dto);

		validateEmployee(employee);

		Employee employeeSaved = employeeRepository.save(employee);

		return employeeMapper.toEmployeeResponseDTO(employeeSaved);
	}

	private void validateEmployee(Employee employee) {
		if (employee.getEmployeeType().ordinal() == 2) {
			if (employee.getCommission() == null) {
				throw new ApplicationException("Comissão deve ser obrigatório para funcionário vendedor.");
			}
			
			if (employee.getCommission().longValue() == 0) {
				throw new ApplicationException("Comissão deve ser obrigatório para funcionário vendedor.");
			}
		}

		if (employee.getSalary().scale() != 2) {
			throw new ApplicationException("Salário deve ter 2 dígitos.");
		}

		if (employee.getSalary().toString().equals("0.00")) {
			throw new ApplicationException("Salário não deve 0.");
		}

		if (existsByNameOrMatriculationOrEmailOrPhoneOrCpf(employee)) {
			throw new ApplicationException("Nome, matrícula, email, telefone ou cpf não deve ser repetido.");
		}
	}

	private boolean existsByNameOrMatriculationOrEmailOrPhoneOrCpf(Employee employee) {
		return employeeRepository.existsByNameOrMatriculationOrEmailOrPhoneOrCpf(employee.getName(),
				employee.getMatriculation(), employee.getEmail(), employee.getPhone(), employee.getCpf());
	}
}