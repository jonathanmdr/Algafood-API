package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;

@Component
public class CityRepositoryImpl implements CityRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<City> list() {
		return manager.createQuery("from City", City.class).getResultList();
	}

	@Override
	public City findById(Long id) {
		return manager.find(City.class, id);
	}

	@Override
	public City save(City city) {
		return manager.merge(city);
	}

	@Override
	public void delete(City city) {
		city = findById(city.getId());
		manager.remove(city);
	}

}
