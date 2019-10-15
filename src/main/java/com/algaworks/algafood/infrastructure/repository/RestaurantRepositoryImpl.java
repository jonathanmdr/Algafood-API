package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Restaurant> list() {
		return manager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Restaurant findById(Long id) {
		return manager.find(Restaurant.class, id);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Restaurant save(Restaurant restaurant) {
		return manager.merge(restaurant);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Long id) {
		Restaurant restaurant = findById(id);
		
		if (restaurant == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(restaurant);
	}

}
