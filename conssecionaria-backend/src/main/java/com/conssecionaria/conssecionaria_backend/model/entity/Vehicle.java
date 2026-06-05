package com.conssecionaria.conssecionaria_backend.model.entity;

import java.math.BigDecimal;
import java.util.List;

import com.conssecionaria.conssecionaria_backend.model.entity.enums.TransmissionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false, unique = true, length = 15)
	private String placa;
	@Column(nullable = false)
	private TransmissionType transmissionType;
	@Column(nullable = false, length = 20)
	private String cor;
	@Column(nullable = false, scale = 2)
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name = "model_id", nullable = false)
	private Model model;
	@OneToMany(mappedBy = "vehicle")
	private List<Item> items;
}