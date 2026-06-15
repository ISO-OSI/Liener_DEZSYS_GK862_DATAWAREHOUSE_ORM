package org.example;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

	@Id
	@Column(name = "product_id")
	private String productID;

	private String productName;

	private String productCategory;

	private Integer productQuantity;

	private String productUnit;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	@JsonBackReference
	private DataWarehouse warehouse;

	public Product() {
	}

	public Product(String productID, String productName, String productCategory,
			Integer productQuantity, String productUnit, DataWarehouse warehouse) {
		this.productID = productID;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productQuantity = productQuantity;
		this.productUnit = productUnit;
		this.warehouse = warehouse;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public DataWarehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(DataWarehouse warehouse) {
		this.warehouse = warehouse;
	}
}
