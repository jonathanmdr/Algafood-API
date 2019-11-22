package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;

@Service
public class StateService {
		
	private static final String MESSAGE_STATE_CONFLICT = "Estado de ID: %d não pode ser excluído, pois está em uso!";
	
	@Autowired
	private StateRepository stateRepository;
	
	@Transactional(readOnly = true)
	public List<State> findAll() {
		return stateRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public State findById(Long id) {
		return stateRepository.findById(id)
				.orElseThrow(() -> new StateNotFoundException(id));
	}
	
	@Transactional
	public State save(State state) {
		return stateRepository.save(state);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			stateRepository.deleteById(id);
			stateRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new StateNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_STATE_CONFLICT, id));
		}
	}

}
