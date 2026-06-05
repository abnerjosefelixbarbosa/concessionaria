package com.conssecionaria.conssecionaria_backend.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false, length = 100)
	private String name;
	@Column(nullable = false, unique = true, length = 14)
	private String document;
	@Column(nullable = false, unique = true, length = 30)
	private String email;
	@Column(nullable = false, unique = true, length = 30)
	private String phone;
	@OneToMany(mappedBy = "customer")
	private List<Sale> sales;
}