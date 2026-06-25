package com.concessionaria.backend.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "models")
public class Model {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;
	@OneToMany(mappedBy = "model")
	private List<Vehicle> vehicles;
	
	public Model() {
		
	}
	
	public Model(String id, String name, Brand brand, List<Vehicle> vehicles) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.vehicles = vehicles;
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
