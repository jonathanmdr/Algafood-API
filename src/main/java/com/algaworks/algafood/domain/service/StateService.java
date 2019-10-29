package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;

@Service
public class StateService {
	
	@Autowired
	private StateRepository stateRepository;
	
	public State save(State state) {
		return stateRepository.save(state);
	}
	
	public void delete(Long id) {
		try {
			stateRepository.deleteById(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException(String.format("Estado de ID: %d não existe!", id));
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format("Estado de ID: %d não pode ser excluído, pois está em uso!", id));
		}
	}

}
