package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Component
public class KitchenRepositoryImpl implements KitchenRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Kitchen> list() {
		return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
	}
	
	@Override
	public Kitchen findById(Long id) {
		return manager.find(Kitchen.class, id);
	}
		
	@Transactional
	@Override
	public Kitchen save(Kitchen kitchen) {
		return manager.merge(kitchen);
	}
	
	@Transactional
	@Override
	public void delete(Kitchen kitchen) {
		kitchen = findById(kitchen.getId());
		manager.remove(kitchen);
	}

}
