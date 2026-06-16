# DEZSYS_GK81_WAREHOUSE_ORM

This project is a small Spring Boot application for the ORM exercise.

It started with the Spring tutorial "Accessing data with MySQL". The tutorial
only had a simple `User` entity. I kept this part in the project and then added
a small data warehouse model with warehouses, products and purchases.

The project uses:

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Gradle

## How to run the project

First a MySQL server must be running. The application expects a database called
`example`.

Example MySQL commands:

```sql
create database example;
use example;
show tables;
```

The connection is configured in:

```text
src/main/resources/application.properties
```

Current database settings:

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/example
spring.datasource.username=root
spring.datasource.password=
```

If your MySQL user or password is different, change these values.

Run the project with the Gradle wrapper:

```bash
./gradlew bootRun
```

On Windows:

```bash
.\gradlew.bat bootRun
```

The application starts on:

```text
http://localhost:8080
```

## How to test it

The old tutorial endpoint is still there:

```bash
curl -X POST "http://localhost:8080/demo/add" -d "name=John" -d "email=john@example.com"
curl "http://localhost:8080/demo/all"
```

Warehouse endpoints:

```bash
curl "http://localhost:8080/demo/warehouses"
curl "http://localhost:8080/demo/warehouses/001"
curl "http://localhost:8080/demo/warehouses/001/products"
curl "http://localhost:8080/demo/warehouses/001/products/00-443175"
```

Update one warehouse:

```bash
curl -X PUT "http://localhost:8080/demo/warehouses/001" -d "warehouseName=Linz Main Station"
```

Other endpoints:

```bash
curl "http://localhost:8080/demo/products"
curl "http://localhost:8080/demo/purchases"
curl "http://localhost:8080/demo/warehouses/001/purchases"
```

LLM sales preview endpoint:

```bash
curl "http://localhost:8080/demo/sales-preview"
```

For the LLM endpoint I used Ollama as the local example. The default URL is:

```text
http://localhost:11434/api/generate
```

The default model is:

```text
llama3.2
```

If Ollama is not running, the endpoint returns a short message that it could not
call the LLM. This is expected.

## Database setup

Hibernate creates or updates the database tables because this setting is used:

```properties
spring.jpa.hibernate.ddl-auto=update
```

So normally I only need to create the database `example`. The tables are created
by the application when it starts.

The sample data is inserted by `SampleDataLoader`. It only inserts the data when
the warehouse, product and purchase tables are empty.

## Entities

### User

`User` is the entity from the Spring tutorial. It has:

* `id`
* `name`
* `email`

This part shows the basic MySQL tutorial.

### DataWarehouse

`DataWarehouse` stores one warehouse location.

Important fields:

* `warehouseID`
* `warehouseName`
* `warehouseAddress`
* `warehousePostalCode`
* `warehouseCity`
* `warehouseCountry`
* `timestamp`

The warehouse ID is the primary key. Example: `001`.

### Product

`Product` stores one product in one warehouse.

Important fields:

* `productID`
* `productName`
* `productCategory`
* `productQuantity`
* `productUnit`
* `warehouse`

The product has a `ManyToOne` relation to `DataWarehouse`. This means many
products can belong to one warehouse.

### Purchase

`Purchase` stores sold products.

Important fields:

* `id`
* `purchaseTime`
* `amount`
* `location`
* `warehouse`
* `product`

A purchase points to one warehouse and one product. This makes it possible to
show where a product was sold and how many pieces were sold.

## Relations between entities

The important relation for the basic task is:

```text
DataWarehouse 1 ---- many Product
```

In Java this is written with:

* `@OneToMany` in `DataWarehouse`
* `@ManyToOne` in `Product`

For purchases there are also relations:

```text
Product 1 ---- many Purchase
DataWarehouse 1 ---- many Purchase
```

I used simple references in the `Purchase` entity for this.

## Repositories

The repositories extend `CrudRepository`. Spring creates the real code for them
at runtime.

### UserRepository

Used for the old tutorial user example.

### DataWarehouseRepository

Used to save and read warehouses. The standard `CrudRepository` methods already
include `findById`, `findAll`, `save` and delete methods.

### ProductRepository

Extra methods:

```java
Iterable<Product> findByWarehouseWarehouseID(String warehouseID);
Optional<Product> findByWarehouseWarehouseIDAndProductID(String warehouseID, String productID);
```

These methods are used to get all products of one warehouse and one exact product
inside one warehouse.

### PurchaseRepository

Extra method:

```java
Iterable<Purchase> findByWarehouseWarehouseID(String warehouseID);
```

This gets all purchases for one warehouse.

## REST endpoints

The controller uses `/demo` like in the Spring tutorial.

| Method | URL | What it does |
| --- | --- | --- |
| POST | `/demo/add` | Adds a tutorial user |
| GET | `/demo/all` | Shows all tutorial users |
| GET | `/demo/warehouses` | Shows all warehouses |
| GET | `/demo/warehouses/{warehouseID}` | Shows one warehouse with its products |
| GET | `/demo/warehouses/{warehouseID}/products` | Shows products of one warehouse |
| GET | `/demo/warehouses/{warehouseID}/products/{productID}` | Shows one product in one warehouse |
| PUT | `/demo/warehouses/{warehouseID}` | Updates one warehouse |
| GET | `/demo/products` | Shows all products |
| GET | `/demo/purchases` | Shows all purchases |
| GET | `/demo/warehouses/{warehouseID}/purchases` | Shows purchases of one warehouse |
| GET | `/demo/sales-preview` | Sends sales data to a local Ollama LLM |

## Sample data

The application inserts:

* 2 warehouse records
* 10 product records
* 260 purchase records

The normal extended task asked for 30 sold product records. I inserted 260
purchase records because the Vertiefung part asks for 250 to 300 training
records. Together with the 10 products this gives 270 training records.

The data is simple test data. The first warehouse is based on the example:

```text
001 - Linz Bahnhof
```

The second warehouse is:

```text
002 - Wien Lager Nord
```

The purchases are generated in a loop. Each product gets purchases on different
days with different amounts.

## What was changed from the original tutorial

The original tutorial only had:

* `User`
* `UserRepository`
* one controller with `/demo/add` and `/demo/all`

I added:

* `DataWarehouse`
* `Product`
* `Purchase`
* repositories for the new entities
* endpoints for warehouses, products and purchases
* sample data loader
* a small Ollama LLM endpoint for the sales preview
* more project documentation in this README

## ORM questions

### 1. What is ORM and how is JPA used?

ORM means Object Relational Mapping. It maps Java objects to database tables.
Instead of writing SQL for every small thing, I can work with Java classes like
`Product` or `DataWarehouse`.

JPA is the Java standard for this. In this project Hibernate is the JPA provider.
Spring Data JPA makes the repositories easier to use.

### 2. What is application.properties used for and where must it be stored?

`application.properties` stores settings for the Spring Boot app. In this project
it stores the MySQL URL, username, password, driver and also the LLM settings.

It must be stored here:

```text
src/main/resources/application.properties
```

### 3. Which annotations are often used for entity types?

Common annotations are:

* `@Entity`
* `@Id`
* `@GeneratedValue`
* `@Column`
* `@OneToMany`
* `@ManyToOne`
* `@JoinColumn`

Important points:

* every entity needs an ID
* relations need the correct annotation on both sides if they are bidirectional
* the entity needs a no-argument constructor
* field names should match what the repository methods use

### 4. What methods do you need for CRUD operations?

From `CrudRepository` the important methods are:

* `save`
* `findById`
* `findAll`
* `deleteById`
* `delete`
* `count`
* `existsById`

Create and update both use `save`. If the ID already exists, it updates the
record. If the ID is new, it inserts a new record.

## Development log

### Step 1

I checked the existing project. It was still very close to the Spring MySQL
tutorial. It had `User`, `UserRepository`, `MainController` and the MySQL
settings.

### Step 2

I added the warehouse entity. I used `warehouseID` as the primary key because the
assignment data already uses IDs like `001`.

### Step 3

I added the product entity and connected it to the warehouse entity with
`@ManyToOne`. The warehouse has a list of products with `@OneToMany`.

### Step 4

I added the purchase entity for sold product records. It stores date/time, amount,
location, product and warehouse.

### Step 5

I added repositories for the new entities. The product repository also has query
methods for finding products inside one warehouse.

### Step 6

I added endpoints in the existing controller. I kept the old tutorial endpoints
because they are still part of the assignment.

### Step 7

I added sample data. There are 2 warehouses, 10 products and 260 purchases.

### Step 8

I added a small LLM preview endpoint. It sends the sold amounts to Ollama. This
needs Ollama to run locally.

## Problems and issues

One problem is that MySQL must already exist and the database `example` must be
created before the app starts.

Another problem is the Java version. This project uses Spring Boot 3, so a modern
JDK is needed. The assignment says Java SDK 18 or higher.

The relation between warehouse and product can create endless JSON output if both
sides are printed again and again. I used Jackson annotations so the product does
not print the warehouse again inside the warehouse response.

The LLM part depends on Ollama. If Ollama is not installed or the model is not
downloaded, the endpoint cannot create a real preview.

## Fulfilled requirements

Implemented:

* Spring Boot MySQL tutorial user example
* MySQL connection in `application.properties`
* warehouse data model
* product data model
* relation between warehouse and product
* purchase data model
* 2 warehouse records
* 10 product records
* more than 30 purchase records
* 250 to 300 training records
* repository methods for warehouse/product lookup
* update endpoint for a warehouse
* local LLM endpoint using Ollama
* ORM questions answered
* development log
* problems section

## Still missing

No cloud LLM like Gemini is included. I used the local Ollama option from the
assignment instead.

There are no automatic unit tests yet. The REST endpoints are listed above for
manual testing.

## Assumptions

I assumed the database name should stay `example` because the tutorial and the
old project already used it.

I assumed the warehouse ID from the XML example is the same as the
`datawarehouseID` mentioned later in the assignment.

I assumed that using Ollama is enough for the LLM part, because the assignment
says local LLMs are allowed.
