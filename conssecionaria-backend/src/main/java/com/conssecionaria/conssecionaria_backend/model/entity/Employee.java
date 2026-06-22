package com.conssecionaria.conssecionaria_backend.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeStatus;
import com.conssecionaria.conssecionaria_backend.model.entity.enums.EmployeeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "matriculation", nullable = false, unique = true, length = 10)
	private String matriculation;
	@Column(name = "email", nullable = false, unique = true, length = 30)
	private String email;
	@Column(name = "phone", nullable = false, unique = true, length = 30)
	private String phone;
	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;
	@Column(name = "cpf", nullable = false, unique = true, length = 11)
	private String cpf;
	@Column(name = "salary", nullable = false, scale = 2)
	private BigDecimal salary = BigDecimal.ZERO;
	@Column(name = "commission")
	private Integer commission;
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_status", nullable = false)
	private EmployeeStatus employeeStatus;
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_type", nullable = false)
	private EmployeeType employeeType;
	@OneToMany(mappedBy = "employee")
	private List<Sale> sales;
}