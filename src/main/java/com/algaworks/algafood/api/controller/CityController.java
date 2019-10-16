package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CityService cityService;
	
	@GetMapping
	public List<City> findAll() {
		return cityRepository.list();
	}
	
	@GetMapping("/{cityId}")
	public ResponseEntity<City> findById(@PathVariable Long cityId) {
		City citySaved = cityRepository.findById(cityId);
		return citySaved != null ? ResponseEntity.ok(citySaved) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody City city) {
		try {
			city = cityService.save(city);
			return ResponseEntity.status(HttpStatus.CREATED).body(city);
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PutMapping("/{cityId}")
	public ResponseEntity<City> update(@PathVariable Long cityId, @RequestBody City city) {
		City citySaved = cityRepository.findById(cityId);
		
		if (citySaved != null) {
			BeanUtils.copyProperties(city, citySaved, "id");
			citySaved = cityService.save(citySaved);
			return ResponseEntity.ok(citySaved);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/cityId")
	public ResponseEntity<City> delete(@PathVariable Long cityId) {
		try {
			cityService.delete(cityId);
			return ResponseEntity.noContent().build();
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(EntityInUseException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
