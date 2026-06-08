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
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false, length = 100)
	private String name;
	@Column(nullable = false, unique = true, length = 10)
	private String matriculation;
	@Column(nullable = false, unique = true, length = 30)
	private String email;
	@Column(nullable = false, unique = true, length = 30)
	private String phone;
	@Column(nullable = false)
	private LocalDate birthDate;
	@Column(nullable = false, unique = true, length = 11)
	private String cpf;
	@Column(nullable = false, scale = 2)
	private BigDecimal salary = BigDecimal.ZERO;
	@Column(nullable = false)
	private Integer commission;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EmployeeStatus employeeStatus;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EmployeeType employeeType;
	@OneToMany(mappedBy = "employee")
	private List<Sale> sales;
}