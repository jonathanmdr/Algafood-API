package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}
	
	public void delete(Long id) {
		try {
			kitchenRepository.delete(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException(String.format("Cozinha de ID: %d não existe!", id));
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format("Cozinha de ID: %d não pode ser excluída, pois está em uso!", id));
		}
	}

}
