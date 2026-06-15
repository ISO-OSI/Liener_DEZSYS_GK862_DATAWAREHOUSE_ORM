package org.example;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {

	Iterable<Purchase> findByWarehouseWarehouseID(String warehouseID);
}
