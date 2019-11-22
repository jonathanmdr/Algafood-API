package com.algaworks.algafood.api;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

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
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantApiTest {
	
	private static final String BUSINESS_VIOLATION_API_ERROR_TITLE = "Violação de regra de negócio";
	private static final String INVALID_DATA_API_ERROR_TITLE = "Dados inválidos";
	private static final Integer INEXISTENT_RESTAURANT = 9999;
	private static Integer AMOUNT_RESTAURANTS;
	
	private static Restaurant americanRestaurant;
	
	private static String jsonRestaurantCorrect;
	private static String jsonRestaurantWithoutFreightRate;
	private static String jsonRestaurantWithoutKitchen;
	private static String jsonRestaurantWithInexistentKitchen;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/restaurants";
		RestAssured.port = this.port;
		
		jsonRestaurantCorrect = ResourceUtils.getContentFromResource("/json/correct/restaurant/restaurant-american-burguer.json");
		jsonRestaurantWithoutFreightRate = ResourceUtils.getContentFromResource("/json/incorrect/restaurant/restaurant-american-burguer-without-freightrate.json");
		jsonRestaurantWithoutKitchen = ResourceUtils.getContentFromResource("/json/incorrect/restaurant/restaurant-american-burguer-without-kitchen.json");
		jsonRestaurantWithInexistentKitchen = ResourceUtils.getContentFromResource("/json/incorrect/restaurant/restaurant-american-burguer-with-inexistent-kitchen.json");
		
		databaseCleaner.clearTables();
		prepareData();
	}
	
	@Test
	public void returningHttpStatusCode200WhenFindRestaurants() {				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void returningAmountRestaurantWhenFindRestaurants() {		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(AMOUNT_RESTAURANTS));
	}
	
	@Test
	public void returningHttpStatusCode201WhenSaveRestaurant() {
		RestAssured.given()
			.body(jsonRestaurantCorrect)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void returningHttpStatusCode400WhenSaveRestaurantWithoutFreightRate() {
		RestAssured.given()
			.body(jsonRestaurantWithoutFreightRate)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(INVALID_DATA_API_ERROR_TITLE));
	}
	
	@Test
	public void returningHttpStatusCode400WhenSaveRestaurantWithoutKitchen() {
		RestAssured.given()
			.body(jsonRestaurantWithoutKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(INVALID_DATA_API_ERROR_TITLE));
	}
	
	@Test
	public void returningHttpStatusCode400WhenSaveRestaurantWithInexistentKitchen() {
		RestAssured.given()
			.body(jsonRestaurantWithInexistentKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(BUSINESS_VIOLATION_API_ERROR_TITLE));
	}
	
	public void returningRestaurantSuccessfullyWhenFindRestaurantExistent() {
		RestAssured.given()
			.pathParam("restaurantId", americanRestaurant.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restaurantId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(americanRestaurant.getName()));
	}
	
	public void returningHttpStatusCode404WhenFindRestaurantInexistent() {
		RestAssured.given()
			.pathParam("restauranId", INEXISTENT_RESTAURANT)
			.accept(ContentType.JSON)
		.when()
			.get("/{restaurantId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepareData() {
		Kitchen americanKitchen = new Kitchen();
		americanKitchen.setName("Americana");		
		kitchenRepository.save(americanKitchen);
		
		Kitchen thaiKitchen = new Kitchen();
		thaiKitchen.setName("Tailandesa");		
		kitchenRepository.save(thaiKitchen);
		
		americanRestaurant = new Restaurant();
		americanRestaurant.setName("American Burguer");
		americanRestaurant.setFreightRate(BigDecimal.TEN);
		americanRestaurant.setKitchen(americanKitchen);				
		americanRestaurant = restaurantRepository.save(americanRestaurant);
		
		Restaurant thaiRestaurant = new Restaurant();
		thaiRestaurant.setName("Tailandes Pizzaria");
		thaiRestaurant.setFreightRate(BigDecimal.TEN);
		thaiRestaurant.setKitchen(americanKitchen);				
		thaiRestaurant = restaurantRepository.save(thaiRestaurant);
		
		AMOUNT_RESTAURANTS = (int) restaurantRepository.count();
	}

}
