package com.concessionaria.backend.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.concessionaria.backend.model.entity.enums.EmployeeStatus;
import com.concessionaria.backend.model.entity.enums.EmployeeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
	
	public Employee() {
		
	}
	
	public Employee(String id, String name, String matriculation, String email, String phone, LocalDate birthDate,
			String cpf, BigDecimal salary, Integer commission, EmployeeStatus employeeStatus, EmployeeType employeeType,
			List<Sale> sales) {
		this.id = id;
		this.name = name;
		this.matriculation = matriculation;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.salary = salary;
		this.commission = commission;
		this.employeeStatus = employeeStatus;
		this.employeeType = employeeType;
		this.sales = sales;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMatriculation() {
		return matriculation;
	}

	public void setMatriculation(String matriculation) {
		this.matriculation = matriculation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public EmployeeStatus getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(EmployeeStatus employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
}
