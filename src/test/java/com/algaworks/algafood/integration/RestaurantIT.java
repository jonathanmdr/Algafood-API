package com.algaworks.algafood.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.algaworks.algafood.util.DatabaseCleaner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class RestaurantIT {
	
	private static final Long INEXISTENT_RESTAURANT = 9999L;
	
	private static Kitchen americanKitchen;

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Before
	public void setUp() {		
		databaseCleaner.clearTables();
		prepareData();
	}
	
	@Test
	public void saveRestaurantWithSuccess() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Restaurante brasa burguer");
		restaurant.setFreightRate(BigDecimal.TEN);		
		restaurant.setKitchen(americanKitchen);
		
		restaurant = restaurantService.save(restaurant);
		
		assertThat(restaurant).isNotNull();
		assertThat(restaurant.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveRestaurantWithNameNull() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(null);
		restaurant.setFreightRate(BigDecimal.TEN);		
		restaurant.setKitchen(americanKitchen);
		
		restaurant = restaurantService.save(restaurant);
	}
	
	@Test(expected = RestaurantNotFoundException.class)
	public void deleteRestaurantNonexistent() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(INEXISTENT_RESTAURANT);
		
		restaurantService.delete(restaurant.getId());
	}
	
	private void prepareData() {		
		americanKitchen = new Kitchen();
		americanKitchen.setName("Americana");
		
		kitchenRepository.save(americanKitchen);
	}

}
