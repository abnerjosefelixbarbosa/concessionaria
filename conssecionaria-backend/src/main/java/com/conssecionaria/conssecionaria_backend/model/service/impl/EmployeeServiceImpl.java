package com.conssecionaria.conssecionaria_backend.model.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeRequestDTO;
import com.conssecionaria.conssecionaria_backend.model.dto.EmployeeResponseDTO;
import com.conssecionaria.conssecionaria_backend.model.entity.Employee;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;
import com.conssecionaria.conssecionaria_backend.model.exception.ApplicationException;
import com.conssecionaria.conssecionaria_backend.model.exception.NotFoundException;
import com.conssecionaria.conssecionaria_backend.model.mapper.EmployeeMapper;
import com.conssecionaria.conssecionaria_backend.model.repository.EmployeeRepository;
import com.conssecionaria.conssecionaria_backend.model.service.EmployeeService;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
	}

	@Transactional
	public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO dto) {
		Employee employee = employeeMapper.toEmployee(dto);

		validateEmployee(employee);

		Employee employeeSaved = employeeRepository.save(employee);

		return employeeMapper.toEmployeeResponseDTO(employeeSaved);
	}

	@Transactional
	public EmployeeResponseDTO updateEmployeeById(String id, EmployeeRequestDTO dto) {
		Employee employee = employeeMapper.toEmployee(dto);

		validateEmployee(employee);

		Employee employeeFound = employeeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		BeanUtils.copyProperties(employee, employeeFound, "id");

		Employee employeeSaved = employeeRepository.save(employeeFound);

		return employeeMapper.toEmployeeResponseDTO(employeeSaved);
	}

	public EmployeeResponseDTO findEmployeeById(String id) {
		Employee employeeFound = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		return employeeMapper.toEmployeeResponseDTO(employeeFound);
	}
	
	@Override
	public Page<EmployeeResponseDTO> listEmployees(Pageable pageable, String name, EmployeeStatus employeeStatus,
			EmployeeType employeeType) {
	    
		Page<Employee> page = employeeRepository.findAllByNameOrEmployeeStatusOrEmployeeType(name, employeeStatus, employeeType, pageable);
		
		return page.map(employeeMapper::toEmployeeResponseDTO);
	}

	private void validateEmployee(Employee employee) {
		if (employee.getEmployeeType() == EmployeeType.SALLER) {
			if (employee.getCommission() == null) {
				throw new ApplicationException("Comissão deve ser obrigatório para funcionário vendedor.");
			}

			if (employee.getCommission().longValue() == 0) {
				throw new ApplicationException("Comissão deve ser obrigatório para funcionário vendedor.");
			}

			if (employee.getCommission().longValue() > 100) {
				throw new ApplicationException("Comissão deve ser maior que 100.");
			}
		} else {
			if (employee.getCommission() != null) {
				throw new ApplicationException("Comissão deve ser obrigatório para funcionário vendedor.");
			}
		}

		if (employee.getSalary().scale() != 2) {
			throw new ApplicationException("Salário deve ter 2 dígitos.");
		}

		if (employee.getSalary().toString().equals("0.00")) {
			throw new ApplicationException("Salário não deve 0.00.");
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