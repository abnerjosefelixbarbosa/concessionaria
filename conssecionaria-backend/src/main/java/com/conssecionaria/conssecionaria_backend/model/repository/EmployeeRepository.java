package com.conssecionaria.conssecionaria_backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conssecionaria.conssecionaria_backend.model.entity.Employee;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	boolean existsByNameOrMatriculationOrEmailOrPhoneOrCpf(String name, String matriculation, String email,
			String phone, String cpf);

	Page<Employee> findAllByNameOrEmployeeStatusOrEmployeeType(String name, EmployeeStatus employeeStatus,
			EmployeeType employeeType, Pageable pageable);
}