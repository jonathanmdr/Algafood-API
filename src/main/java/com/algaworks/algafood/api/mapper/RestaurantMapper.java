package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.api.model.input.RestaurantInput;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestaurantDTO toDto(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantDTO.class);
	}
	
	public List<RestaurantDTO> toCollectionDto(List<Restaurant> restaurants) {
		return restaurants.stream().map(restaurant -> toDto(restaurant)).collect(Collectors.toList());
	}
	
	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);		
	}
	
	public RestaurantInput toInputObject(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantInput.class);		
	}
	
	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		/*
		 * Para evitar a exception: org.hibernate.HibernateException: identifier of an instance of 
		 * com.algaworks.algafood.domain.model.Kitchen was altered from 1 to 2
		 */		
		restaurant.setKitchen(new Kitchen());
		
		modelMapper.map(restaurantInput, restaurant);		
	}

}
