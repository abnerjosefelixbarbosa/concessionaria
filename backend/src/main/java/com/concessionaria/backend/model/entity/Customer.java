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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}