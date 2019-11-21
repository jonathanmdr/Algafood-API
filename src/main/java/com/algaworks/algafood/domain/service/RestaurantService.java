package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	private static final String MESSAGE_RESTAURANT_CONFLICT = "Restaurante de ID: %d não pode ser excluído, pois está em uso!";
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private KitchenService kitchenService;
	
	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}
	
	public Restaurant findById(Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(id));
	}
	
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen = kitchenService.findById(kitchenId);
		
		restaurant.setKitchen(kitchen);
		
		return restaurantRepository.save(restaurant);
	}
	
	public void delete(Long id) {
		try {
			restaurantRepository.deleteById(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new RestaurantNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_RESTAURANT_CONFLICT, id));
		}
	}

}
