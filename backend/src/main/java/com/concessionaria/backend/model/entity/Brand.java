package com.concessionaria.backend.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "brands")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;
	@OneToMany(mappedBy = "brand")
	private List<Model> models;

	public Brand() {
		
	}

	public Brand(String id, String name, List<Model> models) {
		this.id = id;
		this.name = name;
		this.models = models;
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

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}
}
