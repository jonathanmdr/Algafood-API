package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.kitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {
	
	private static final String MESSAGE_KITCHEN_CONFLICT = "Cozinha de ID: %d não pode ser excluída, pois está em uso!";
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Transactional(readOnly = true)
	public Page<Kitchen> findAll(Pageable pageable) {
		return kitchenRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Kitchen findById(Long id) {
		return kitchenRepository.findById(id)
				.orElseThrow(() -> new kitchenNotFoundException(id));
	}
	
	@Transactional
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			kitchenRepository.deleteById(id);
			kitchenRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new kitchenNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_KITCHEN_CONFLICT, id));
		}
	}

}
