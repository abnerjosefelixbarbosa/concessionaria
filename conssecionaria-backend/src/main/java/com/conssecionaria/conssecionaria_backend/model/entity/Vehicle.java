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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "plate", nullable = false, unique = true, length = 15)
	private String plate;
	@Column(name = "transmission_type", nullable = false)
	private TransmissionType transmissionType;
	@Column(name = "color", nullable = false, length = 20)
	private String color;
	@Column(name = "price", nullable = false, scale = 2)
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name = "model_id", nullable = false)
	private Model model;
	@OneToMany(mappedBy = "vehicle")
	private List<Item> items;
}