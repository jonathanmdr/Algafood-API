package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.mapper.CityMapperV2;
import com.algaworks.algafood.api.v2.model.CityDTOV2;
import com.algaworks.algafood.api.v2.model.input.CityInputV2;
import com.algaworks.algafood.core.web.AlgaMediaTypesCustom;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.service.CityService;

@RestController
@RequestMapping(path = "/cities", produces = AlgaMediaTypesCustom.V2_APPLICATION_JSON_VALUE)
public class CityControllerV2 {

	@Autowired
	private CityService cityService;

	@Autowired
	private CityMapperV2 cityMapper;
	
	@GetMapping
	public CollectionModel<CityDTOV2> findAll() {
		return cityMapper.toCollectionModel(cityService.findAll());
	}
	
	@GetMapping("/{cityId}")
	public CityDTOV2 findById(@PathVariable Long cityId) {
		return cityMapper.toModel(cityService.findById(cityId));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CityDTOV2 save(@RequestBody @Valid CityInputV2 cityInput) {
		try {
			City city = cityMapper.toDomainObject(cityInput);
			CityDTOV2 cityDto = cityMapper.toModel(cityService.save(city));

			ResourceUriHelper.addUriResponseHeader(cityDto.getIdCity());

			return cityDto;
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	@PutMapping("/{cityId}")
	public CityDTOV2 update(@PathVariable Long cityId, @RequestBody @Valid CityInputV2 cityInput) {
		try {
			City cityCurrent = cityService.findById(cityId);

			cityMapper.copyToDomainObject(cityInput, cityCurrent);

			return cityMapper.toModel(cityService.save(cityCurrent));
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cityId) {
		cityService.delete(cityId);
	}

}
