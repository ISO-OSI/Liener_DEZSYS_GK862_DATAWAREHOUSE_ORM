package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class DataWarehouse {

	@Id
	@Column(name = "warehouse_id")
	private String warehouseID;

	private String warehouseName;

	private String warehouseAddress;

	private String warehousePostalCode;

	private String warehouseCity;

	private String warehouseCountry;

	@Column(name = "warehouse_timestamp")
	private LocalDateTime timestamp;

	@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Product> products = new ArrayList<>();

	public DataWarehouse() {
	}

	public DataWarehouse(String warehouseID, String warehouseName, String warehouseAddress,
			String warehousePostalCode, String warehouseCity, String warehouseCountry, LocalDateTime timestamp) {
		this.warehouseID = warehouseID;
		this.warehouseName = warehouseName;
		this.warehouseAddress = warehouseAddress;
		this.warehousePostalCode = warehousePostalCode;
		this.warehouseCity = warehouseCity;
		this.warehouseCountry = warehouseCountry;
		this.timestamp = timestamp;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public String getWarehousePostalCode() {
		return warehousePostalCode;
	}

	public void setWarehousePostalCode(String warehousePostalCode) {
		this.warehousePostalCode = warehousePostalCode;
	}

	public String getWarehouseCity() {
		return warehouseCity;
	}

	public void setWarehouseCity(String warehouseCity) {
		this.warehouseCity = warehouseCity;
	}

	public String getWarehouseCountry() {
		return warehouseCountry;
	}

	public void setWarehouseCountry(String warehouseCountry) {
		this.warehouseCountry = warehouseCountry;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
