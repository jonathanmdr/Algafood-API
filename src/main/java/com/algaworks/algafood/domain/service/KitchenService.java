package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.kitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {
	
	private static final String MESSAGE_KITCHEN_CONFLICT = "Cozinha de ID: %d não pode ser excluída, pois está em uso!";
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	public List<Kitchen> findAll() {
		return kitchenRepository.findAll();
	}
	
	public Kitchen findById(Long id) {
		return kitchenRepository.findById(id)
				.orElseThrow(() -> new kitchenNotFoundException(id));
	}
	
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}
	
	public void delete(Long id) {
		try {
			kitchenRepository.deleteById(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new kitchenNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_KITCHEN_CONFLICT, id));
		}
	}

}
