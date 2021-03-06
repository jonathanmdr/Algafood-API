package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepository;

@Service
public class CityService {
	
	private static final String MESSAGE_CITY_CONFLICT = "Cidade de ID: %d não pode ser excluída, pois está em uso!";
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateService stateService;
	
	@Transactional(readOnly = true)
	public List<City> findAll() {
		return cityRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public City findById(Long id) {
		return cityRepository.findById(id)
				.orElseThrow(() -> new CityNotFoundException(id));
	}
	
	@Transactional
	public City save(City city) {
		Long stateId = city.getState().getId();
		State state =  stateService.findById(stateId);
		
		city.setState(state);
		
		return cityRepository.save(city);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
			cityRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new CityNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_CITY_CONFLICT, id));
		}
	}

}
