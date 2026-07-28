package com.concessionaria.backend.model.entity;

import java.util.List;

import com.concessionaria.backend.model.entity.enums.CustomerType;

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
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "document", nullable = false, unique = true, length = 14)
	private String document;
	@Column(name = "email", nullable = false, unique = true, length = 30)
	private String email;
	@Column(name = "phone", nullable = false, unique = true, length = 30)
	private String phone;
	@Enumerated(EnumType.STRING)
	@Column(name = "customer_type", nullable = false)
	private CustomerType customerType; 
	@OneToMany(mappedBy = "customer")
	private List<Sale> sales;
	
	public Customer() {
	}
	
	public Customer(String id, String name, String document, String email, String phone, CustomerType customerType,
			List<Sale> sales) {
		this.id = id;
		this.name = name;
		this.document = document;
		this.email = email;
		this.phone = phone;
		this.customerType = customerType;
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

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
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

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
}