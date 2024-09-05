# Spring Events Code Kata

The intent of this Kata is to practice working with spring application events and concurrent programming techniques.

The project comes with a set of Spring Boot tests and the minimal code needed for them to compile.  The goal of the exercise is to implement the code needed to get the tests to pass.  The project comes with a `compose.yaml` file that will spin up a PostGRES docker container.

### Scenarios

#### Create Catalog
When a catalog is created, implement logic that will assign all existing products to the new catalog asynchronously 

#### Create Product
When a product is created, trigger an event that will establish inventory for the new product.

#### Get Catalog with Products
Retrieve a catalog and then retrieve the products and convert them to records asynchronously.

#### Get Products on Sale
Iterate through all products and select ones that should be sold at a discount.  The solution should be implemented so that it conforms to [OCP](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design#open-closed-principle).  Currently, there is only an `InventoryChecker` that will decide if a product should be put on sale, but the solution should support adding any number of additional checkers with little to no changes to existing code.  All of the checker logic should be executed concurrently.  If any one checker determines that the product should be put on sale, then that product should be included in the results.

### Prerequisites 

This project requires the following tools to be installed.
* Java 21
* Docker

### The Domain Model

This project contains four domains: Catalog, Product, Inventory, and Category.  Catalog, Product, and Category are independent entities and can exist on their own.  Inventory requires the existence of a product for which the inventory data pertains.  Catalogs contain a collection or products and products can belong to multiple catalogs.  A category contains multiple products, but a product can only belong to one category.  Each product contains a sku which must be unique across all products and may have an optional inventory status associated with it. 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#using.devtools)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#features.docker-compose)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.

