package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.kitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KitchenIT {

	@Autowired
	private KitchenService kitchenService;
	
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
		kitchen.setId(1L);
		
		kitchenService.delete(kitchen.getId());
	}
	
	@Test(expected = kitchenNotFoundException.class)
	public void deleteKitchenNonexistent() {
		Kitchen kitchen = new Kitchen();
		kitchen.setId(99999L);
		
		kitchenService.delete(kitchen.getId());
	}

}
