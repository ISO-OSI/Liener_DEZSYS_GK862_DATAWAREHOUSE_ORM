package org.example;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {

	Iterable<Product> findByWarehouseWarehouseID(String warehouseID);

	Optional<Product> findByWarehouseWarehouseIDAndProductID(String warehouseID, String productID);
}
