package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;

@Component
public class StateRepositoryImpl implements StateRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<State> list() {
		return manager.createQuery("from State", State.class).getResultList();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public State findById(Long id) {
		return manager.find(State.class, id);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public State save(State state) {
		return manager.merge(state);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Long id) {
		State state = findById(id);
		
		if (state == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(state);
	}

}
