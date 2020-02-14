package com.algaworks.algafood.api.v2.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CityControllerV2;
import com.algaworks.algafood.api.v2.model.CityDTOV2;
import com.algaworks.algafood.api.v2.model.input.CityInputV2;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;

@Component
public class CityMapperV2 extends RepresentationModelAssemblerSupport<City, CityDTOV2> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksV2 algaLinks;

	public CityMapperV2() {
		super(CityControllerV2.class, CityDTOV2.class);
	}

	@Override
	public CityDTOV2 toModel(City city) {
		CityDTOV2 cityDto = createModelWithId(city.getId(), city);
		modelMapper.map(city, cityDto);

		cityDto.add(algaLinks.linkToCities("cities"));

		return cityDto;
	}

	@Override
	public CollectionModel<CityDTOV2> toCollectionModel(Iterable<? extends City> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToCities());
	}

	public City toDomainObject(CityInputV2 cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInputV2 cityInput, City city) {
		/*
		 * Para evitar a exception: org.hibernate.HibernateException: identifier of an
		 * instance of com.algaworks.algafood.domain.model.State was altered from 1 to 2
		 */
		city.setState(new State());

		modelMapper.map(cityInput, city);
	}

}
