package com.concessionaria.backend.model.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.concessionaria.backend.model.dto.EmployeeRequestDTO;
import com.concessionaria.backend.model.dto.EmployeeResponseDTO;
import com.concessionaria.backend.model.entity.Employee;
import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;
import com.concessionaria.backend.model.exception.ApplicationException;
import com.concessionaria.backend.model.exception.NotFoundException;
import com.concessionaria.backend.model.mapper.EmployeeMapper;
import com.concessionaria.backend.model.repository.EmployeeRepository;
import com.concessionaria.backend.model.service.EmployeeService;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Transactional
	public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO dto) {
		Employee employee = EmployeeMapper.toEmployee(dto);

		validateEmployee(employee);

		Employee employeeSaved = employeeRepository.save(employee);

		return EmployeeMapper.toEmployeeResponseDTO(employeeSaved);
	}

	@Transactional
	public EmployeeResponseDTO updateEmployeeById(String id, EmployeeRequestDTO dto) {
		Employee employee = EmployeeMapper.toEmployee(dto);

		validateEmployee(employee);

		Employee employeeFound = employeeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		BeanUtils.copyProperties(employee, employeeFound, "id");

		employeeRepository.save(employeeFound);

		return EmployeeMapper.toEmployeeResponseDTO(employeeFound);
	}

	public EmployeeResponseDTO findEmployeeById(String id) {
		Employee employeeFound = employeeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Id deve ser existente."));

		return EmployeeMapper.toEmployeeResponseDTO(employeeFound);
	}

	public Page<EmployeeResponseDTO> listEmployeesFilteredByNameAndEmployeeStatusAndEmployeeType(String name,
			EmployeeStatus employeeStatus, EmployeeType employeeType, Pageable pageable) {
		Page<Employee> page = employeeRepository.listEmployeesFilteredByNameAndEmployeeStatusAndEmployeeType(name,
				employeeStatus, employeeType, pageable);

		return page.map(EmployeeMapper::toEmployeeResponseDTO);
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
				throw new ApplicationException("Comissão deve ser menor que 100.");
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

		if (isExistsByNameOrMatriculationOrEmailOrPhoneOrCpf(employee)) {
			throw new ApplicationException("Nome, matrícula, email, telefone ou cpf não deve ser repetido.");
		}
	}

	private boolean isExistsByNameOrMatriculationOrEmailOrPhoneOrCpf(Employee employee) {
		return employeeRepository.existsByNameOrMatriculationOrEmailOrPhoneOrCpf(employee.getName(),
				employee.getMatriculation(), employee.getEmail(), employee.getPhone(), employee.getCpf());
	}
}
