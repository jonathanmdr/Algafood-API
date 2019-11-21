package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.hasItems;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KitchenApiTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/kitchens";
		RestAssured.port = this.port;
		
		flyway.migrate();
	}
	
	@Test
	public void returningStatus200WhenFindKitchens() {				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returning4KitchensWhenFindKitchens() {		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", Matchers.hasSize(4))
			.body("name", hasItems("Indiana", "Tailandesa"));
	}
	
	@Test
	public void returningStatus201WhenSaveKitchen() {		
		RestAssured.given()
			.body("{ \"name\": \"Chinesa\" }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

}
