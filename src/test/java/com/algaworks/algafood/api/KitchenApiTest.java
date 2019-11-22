package com.algaworks.algafood.api;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenApiTest {
	
	private static final Integer INEXISTENT_KITCHEN = 9999;
	private static Integer AMOUNT_KITCHENS;	
	
	private static Kitchen americanKitchen;
	private static String jsonChinesekitchen;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/kitchens";
		RestAssured.port = this.port;
		
		jsonChinesekitchen = ResourceUtils.getContentFromResource("/json/correct/kitchen/chinese-kitchen.json");
		
		databaseCleaner.clearTables();
		prepareData();
	}
	
	@Test
	public void returningHttpStatusCode200WhenFindKitchens() {				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returningAmountKitchensWhenFindKitchens() {		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(AMOUNT_KITCHENS));
	}
	
	@Test
	public void returningHttpStatusCode201WhenSaveKitchen() {
		RestAssured.given()
			.body(jsonChinesekitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	public void returningKitchenSuccessfullyWhenFindKitchenExistent() {
		RestAssured.given()
			.pathParam("kitchenId", americanKitchen.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(americanKitchen.getName()));
	}
	
	public void returningHttpStatusCode404WhenFindKitchenInexistent() {
		RestAssured.given()
			.pathParam("kitchenId", INEXISTENT_KITCHEN)
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepareData() {
		Kitchen thaiKitchen = new Kitchen();
		thaiKitchen.setName("Tailandesa");
		
		americanKitchen = new Kitchen();
		americanKitchen.setName("Americana");
		
		kitchenRepository.save(thaiKitchen);
		kitchenRepository.save(americanKitchen);
		
		AMOUNT_KITCHENS = (int) kitchenRepository.count();
	}

}
