package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;

@Component
public class CityRepositoryImpl implements CityRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<City> list() {
		return manager.createQuery("from City", City.class).getResultList();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public City findById(Long id) {
		return manager.find(City.class, id);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public City save(City city) {
		return manager.merge(city);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Long id) {
		City city = findById(id);
		
		if (city == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(city);
	}

}
