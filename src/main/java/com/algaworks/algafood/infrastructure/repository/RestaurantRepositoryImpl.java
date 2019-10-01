package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurant> list() {
		return manager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}

	@Override
	public Restaurant findById(Long id) {
		return manager.find(Restaurant.class, id);
	}

	@Override
	public Restaurant save(Restaurant restaurant) {
		return manager.merge(restaurant);
	}

	@Override
	public void delete(Restaurant restaurant) {
		restaurant = findById(restaurant.getId());
		manager.remove(restaurant);
	}

}
