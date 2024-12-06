package com.quoc.microservices.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Start the server with a random port
class ProductServiceApplicationTests {

	@ServiceConnection // Injects the connection (which define in app properties) to the service in the test
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort //Injects the random port that was assigned to the server
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start(); // Start the container
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
					"name": "iPhone15",
				    "description": "iPhone15 is a smartphone from Apple",
				    "price" : "1000"
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.body("name", org.hamcrest.Matchers.equalTo("iPhone15"))
				.body("description", org.hamcrest.Matchers.equalTo("iPhone15 is a smartphone from Apple"))
				.body("price", org.hamcrest.Matchers.equalTo(1000));
	}

}
