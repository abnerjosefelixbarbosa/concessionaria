package com.conssecionaria.conssecionaria_backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.conssecionaria.conssecionaria_backend.model.entity.Employee;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	boolean existsByNameOrMatriculationOrEmailOrPhoneOrCpf(String name, String matriculation, String email,
			String phone, String cpf);

	@Query("""
			    SELECT e
			    FROM Employee e
			    WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))
			      AND (:employeeStatus IS NULL OR e.employeeStatus = :employeeStatus)
			      AND (:employeeType IS NULL OR e.employeeType = :employeeType)
			""")
	Page<Employee> listEmployees(@Param("name") String name, @Param("employeeStatus") EmployeeStatus employeeStatus,
			@Param("employeeType") EmployeeType employeeType, Pageable pageable);
}