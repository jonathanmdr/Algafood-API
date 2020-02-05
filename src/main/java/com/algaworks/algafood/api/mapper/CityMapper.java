package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CityController;
import com.algaworks.algafood.api.model.CityDTO;
import com.algaworks.algafood.api.model.input.CityInput;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;

@Component
public class CityMapper extends RepresentationModelAssemblerSupport<City, CityDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public CityMapper() {
		super(CityController.class, CityDTO.class);
	}

	@Override
	public CityDTO toModel(City city) {
		CityDTO cityDto = createModelWithId(city.getId(), city);
		modelMapper.map(city, cityDto);
		
		cityDto.add(algaLinks.linkToCities("cities"));
		cityDto.getState().add(algaLinks.linkToState(cityDto.getState().getId()));

		return cityDto;
	}

	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInput cityInput, City city) {
		/*
		 * Para evitar a exception: org.hibernate.HibernateException: identifier of an
		 * instance of com.algaworks.algafood.domain.model.State was altered from 1 to 2
		 */
		city.setState(new State());

		modelMapper.map(cityInput, city);
	}

}
