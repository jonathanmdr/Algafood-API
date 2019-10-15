package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen = kitchenRepository.findById(kitchenId);
		
		if (kitchen == null) {
			throw new EntityNotFoundException(String.format("A cozinha de ID: %d não existe!", kitchenId));
		}
		
		return restaurantRepository.save(restaurant);
	}
	
	public void delete(Long id) {
		try {
			restaurantRepository.delete(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException(String.format("Restaurante de ID: %d não existe!", id));
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format("Restaurante de ID: %d não pode ser excluído, pois está em uso!", id));
		}
	}

}
