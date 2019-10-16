package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.repository.StateRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	public City save(City city) {
		Long stateId = city.getState().getId();
		State state =  stateRepository.findById(stateId);
		
		if (state == null) {
			throw new EntityNotFoundException(String.format("O estado de ID: %d não existe!", stateId));
		}
		
		return cityRepository.save(city);
	}
	
	public void delete(Long id) {
		try {
			cityRepository.delete(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException(String.format("Cidade de ID: %d não existe!", id));
		} catch(DataIntegrityViolationException ex) {
			throw new EntityNotFoundException(String.format("Cidade de ID: %d não pode ser excluída, pois está em uso!", id));
		}
	}

}
