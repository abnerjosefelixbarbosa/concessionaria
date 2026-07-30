package com.concessionaria.backend.model.entity;

import java.math.BigDecimal;
import java.util.List;

import com.concessionaria.backend.model.entity.enums.TransmissionType;
import com.concessionaria.backend.model.entity.enums.VehicleStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "plate", nullable = false, unique = true, length = 15)
	private String plate;
	@Enumerated(EnumType.STRING)
	@Column(name = "transmission_type", nullable = false)
	private TransmissionType transmissionType;
	@Enumerated(EnumType.STRING)
	@Column(name = "vehicle_status", nullable = false)
	private VehicleStatus vehicleStatus;
	@Column(name = "color", nullable = false, length = 20)
	private String color;
	@Column(name = "price", nullable = false, scale = 2)
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name = "model_id", nullable = false)
	private Model model;
	@OneToMany(mappedBy = "vehicle")
	private List<Item> items;
	
	public Vehicle() {
		
	}

	public Vehicle(String id, String plate, TransmissionType transmissionType,
			VehicleStatus vehicleStatus, String color, BigDecimal price, Model model, List<Item> items) {
		this.id = id;
		this.plate = plate;
		this.transmissionType = transmissionType;
		this.vehicleStatus = vehicleStatus;
		this.color = color;
		this.price = price;
		this.model = model;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public TransmissionType getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
