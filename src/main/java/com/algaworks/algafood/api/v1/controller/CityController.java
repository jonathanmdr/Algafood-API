package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algaworks.algafood.api.v1.controller.openapi.controller.CityControllerOpenApi;
import com.algaworks.algafood.api.v1.mapper.CityMapper;
import com.algaworks.algafood.api.v1.model.CityDTO;
import com.algaworks.algafood.api.v1.model.input.CityInput;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.service.CityService;

@RestController
@RequestMapping(path = "/v1/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

	@Autowired
	private CityService cityService;

	@Autowired
	private CityMapper cityMapper;

	@Override
	@Security.Cities.AllowedConsult
	@GetMapping
	public CollectionModel<CityDTO> findAll() {
		return cityMapper.toCollectionModel(cityService.findAll());
	}

	@Override
	@Security.Cities.AllowedConsult
	@GetMapping("/{cityId}")
	public CityDTO findById(@PathVariable Long cityId) {
		return cityMapper.toModel(cityService.findById(cityId));
	}

	@Override
	@Security.Cities.AllowedEdit
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CityDTO save(@RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityMapper.toDomainObject(cityInput);
			CityDTO cityDto = cityMapper.toModel(cityService.save(city));

			ResourceUriHelper.addUriResponseHeader(cityDto.getId());

			return cityDto;
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	@Override
	@Security.Cities.AllowedEdit
	@PutMapping("/{cityId}")
	public CityDTO update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
		try {
			City cityCurrent = cityService.findById(cityId);

			cityMapper.copyToDomainObject(cityInput, cityCurrent);

			return cityMapper.toModel(cityService.save(cityCurrent));
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	@Override
	@Security.Cities.AllowedEdit
	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cityId) {
		cityService.delete(cityId);
	}

}
