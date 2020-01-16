package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.controller.openapi.CityControllerOpenApi;
import com.algaworks.algafood.api.mapper.CityMapper;
import com.algaworks.algafood.api.model.CityDTO;
import com.algaworks.algafood.api.model.input.CityInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController implements CityControllerOpenApi {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityMapper cityMapper;
		
	@GetMapping
	public List<CityDTO> findAll() {
		return cityMapper.toCollectionDto(cityService.findAll());
	}
		
	@GetMapping("/{cityId}")
	public CityDTO findById(@PathVariable Long cityId) {
		return cityMapper.toDto(cityService.findById(cityId));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CityDTO save(@RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityMapper.toDomainObject(cityInput);
			return cityMapper.toDto(cityService.save(city));
		} catch(StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
	
	@PutMapping("/{cityId}")
	public CityDTO update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
		try {
			City cityCurrent = cityService.findById(cityId);
						
			cityMapper.copyToDomainObject(cityInput, cityCurrent);		
		
			return cityMapper.toDto(cityService.save(cityCurrent));
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
