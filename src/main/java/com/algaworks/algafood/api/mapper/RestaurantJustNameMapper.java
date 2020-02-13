package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.model.RestaurantJustNameDTO;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantJustNameMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantJustNameDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public RestaurantJustNameMapper() {
		super(RestaurantController.class, RestaurantJustNameDTO.class);
	}

	@Override
	public RestaurantJustNameDTO toModel(Restaurant restaurant) {
		RestaurantJustNameDTO restaurantJustNameDto = createModelWithId(restaurant.getId(), restaurant);
		modelMapper.map(restaurant, restaurantJustNameDto);

		restaurantJustNameDto.add(algaLinks.linkToRestaurants("restaurants"));

		return restaurantJustNameDto;
	}

	@Override
	public CollectionModel<RestaurantJustNameDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurants());
	}

}
