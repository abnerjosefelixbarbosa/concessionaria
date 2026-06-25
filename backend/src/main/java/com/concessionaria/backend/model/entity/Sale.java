package com.concessionaria.backend.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.concessionaria.backend.model.entity.enums.PaymentType;

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
@Table(name = "sales")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	@Column(name = "sale_date", nullable = false)
	private LocalDate saleDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type", nullable = false)
	private PaymentType paymentType;
	@Column(name = "total_value", nullable = false, scale = 2)
	private BigDecimal totalValue;
	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	@OneToMany(mappedBy = "sale")
	private List<Item> items;
	
	public Sale() {
		
	}
	
	public Sale(String id, LocalDate saleDate, PaymentType paymentType, BigDecimal totalValue, Employee employee,
			Customer customer, List<Item> items) {
		this.id = id;
		this.saleDate = saleDate;
		this.paymentType = paymentType;
		this.totalValue = totalValue;
		this.employee = employee;
		this.customer = customer;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
