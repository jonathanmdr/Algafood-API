package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CityDTO;
import com.algaworks.algafood.api.model.input.CityInput;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;

@Component
public class CityMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CityDTO toDto(City city) {
		return modelMapper.map(city, CityDTO.class);
	}
	
	public List<CityDTO> toCollectionDto(List<City> cities) {
		return cities.stream().map(city -> toDto(city)).collect(Collectors.toList());
	}
	
	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}
	
	public void copyToDomainObject(CityInput cityInput, City city) {
		/*
		 * Para evitar a exception: org.hibernate.HibernateException: identifier of an instance of 
		 * com.algaworks.algafood.domain.model.State was altered from 1 to 2
		 */
		city.setState(new State());
		
		modelMapper.map(cityInput, city);
	}

}
