package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Component
public class KitchenRepositoryImpl implements KitchenRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Kitchen> list() {
		return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Kitchen findById(Long id) {
		return manager.find(Kitchen.class, id);
	}
		
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Kitchen save(Kitchen kitchen) {
		return manager.merge(kitchen);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Long id) {
		Kitchen kitchen = findById(id);
		
		if (kitchen == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(kitchen);
	}

}
