package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping
	public List<City> findAll() {
		return cityService.findAll();
	}
	
	@GetMapping("/{cityId}")
	public City findById(@PathVariable Long cityId) {
		return cityService.findById(cityId);
	}
	
	@PostMapping
	public City save(@RequestBody City city) {
		try {
			return city = cityService.save(city);
		} catch(StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
	
	@PutMapping("/{cityId}")
	public City update(@PathVariable Long cityId, @RequestBody City city) {
		try {
			City cityCurrent = cityService.findById(cityId);
						
			BeanUtils.copyProperties(city, cityCurrent, "id");
		
		
			return cityService.save(cityCurrent);
		} catch(StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
	
	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cityId) {
		cityService.delete(cityId);
	}		

}
