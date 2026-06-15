package org.example;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "purchase_time")
	private LocalDateTime purchaseTime;

	private Integer amount;

	private String location;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private DataWarehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public Purchase() {
	}

	public Purchase(LocalDateTime purchaseTime, Integer amount, String location,
			DataWarehouse warehouse, Product product) {
		this.purchaseTime = purchaseTime;
		this.amount = amount;
		this.location = location;
		this.warehouse = warehouse;
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(LocalDateTime purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public DataWarehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(DataWarehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
