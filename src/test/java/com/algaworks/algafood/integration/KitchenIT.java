package com.algaworks.algafood.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.kitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.KitchenService;
import com.algaworks.algafood.util.DatabaseCleaner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class KitchenIT {
	
	private static final Long INEXISTENT_KITCHEN = 9999L;
	private static Kitchen americanKitchen;

	@Autowired
	private KitchenService kitchenService;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Before
	public void setUp() {		
		databaseCleaner.clearTables();
		prepareData();
	}
	
	@Test
	public void saveKitchenWithSuccess() {
		Kitchen kitchen = new Kitchen();
		kitchen.setName("Chinesa");
		
		kitchen = kitchenService.save(kitchen);
		
		assertThat(kitchen).isNotNull();
		assertThat(kitchen.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveKitchenWithNameNull() {
		Kitchen kitchen = new Kitchen();
		kitchen.setName(null);
		
		kitchen = kitchenService.save(kitchen);
	}
	
	@Test(expected = EntityInUseException.class)
	public void deleteKitchenInUse() {
		Kitchen kitchen = new Kitchen();
		kitchen.setId(americanKitchen.getId());
		
		kitchenService.delete(kitchen.getId());
	}
	
	@Test(expected = kitchenNotFoundException.class)
	public void deleteKitchenNonexistent() {
		Kitchen kitchen = new Kitchen();
		kitchen.setId(INEXISTENT_KITCHEN);
		
		kitchenService.delete(kitchen.getId());
	}
	
	private void prepareData() {		
		americanKitchen = new Kitchen();
		americanKitchen.setName("Americana");
		kitchenRepository.save(americanKitchen);
		
		Restaurant americanRestaurant = new Restaurant();
		americanRestaurant.setName("American Burguer");
		americanRestaurant.setFreightRate(BigDecimal.TEN);
		americanRestaurant.setKitchen(americanKitchen);
		restaurantRepository.save(americanRestaurant);		
	}

}
