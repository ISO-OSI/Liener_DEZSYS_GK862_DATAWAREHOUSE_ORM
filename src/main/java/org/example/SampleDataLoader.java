package org.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

	@Autowired
	private DataWarehouseRepository dataWarehouseRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Override
	public void run(String... args) {
		if (dataWarehouseRepository.count() > 0 || productRepository.count() > 0 || purchaseRepository.count() > 0) {
			return;
		}

		DataWarehouse w1 = new DataWarehouse("001", "Linz Bahnhof", "Bahnhofsstrasse 27/9",
				"4020", "Linz", "Austria", LocalDateTime.of(2021, 9, 12, 8, 52, 39, 77000000));
		DataWarehouse w2 = new DataWarehouse("002", "Wien Lager Nord", "Industriestrasse 15",
				"1210", "Wien", "Austria", LocalDateTime.of(2021, 9, 13, 9, 15, 12, 0));

		dataWarehouseRepository.save(w1);
		dataWarehouseRepository.save(w2);

		Product p1 = new Product("00-443175", "Bio Orangensaft Sonne", "Getraenk", 2500, "Packung 1L", w1);
		Product p2 = new Product("00-871895", "Bio Apfelsaft Gold", "Getraenk", 3420, "Packung 1L", w1);
		Product p3 = new Product("00-220010", "Vollkornbrot Hausgemacht", "Backware", 180, "Stueck", w1);
		Product p4 = new Product("00-551234", "Bergkaese Mild", "Milchprodukt", 730, "Packung 250g", w1);
		Product p5 = new Product("00-771111", "Tomaten Sugo", "Gemuese", 900, "Glas 500g", w1);
		Product p6 = new Product("00-991001", "Mineralwasser Still", "Getraenk", 5000, "Flasche 1.5L", w2);
		Product p7 = new Product("00-991002", "Mineralwasser Prickelnd", "Getraenk", 4600, "Flasche 1.5L", w2);
		Product p8 = new Product("00-991003", "Reis Langkorn", "Grundnahrung", 1200, "Packung 1kg", w2);
		Product p9 = new Product("00-991004", "Kaffee Bohnen", "Genussmittel", 350, "Packung 500g", w2);
		Product p10 = new Product("00-991005", "Tiefkuehl Pizza", "Fertiggericht", 880, "Packung", w2);

		Product[] products = new Product[] { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 };
		for (Product product : products) {
			productRepository.save(product);
		}

		for (int i = 0; i < 260; i++) {
			Product product = products[i % products.length];
			DataWarehouse warehouse = product.getWarehouse();
			int amount = 3 + (i % 18);
			LocalDateTime time = LocalDateTime.of(2024, 1, 1, 8, 30, 0).plusDays(i).plusHours(i % 7);
			purchaseRepository.save(new Purchase(time, amount, warehouse.getWarehouseName(), warehouse, product));
		}
	}
}
