package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.model.RestaurantSummaryDTO;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantSummaryMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public RestaurantSummaryMapper() {
		super(RestaurantController.class, RestaurantSummaryDTO.class);
	}

	@Override
	public RestaurantSummaryDTO toModel(Restaurant restaurant) {
		RestaurantSummaryDTO restaurantSummaryDto = createModelWithId(restaurant.getId(), restaurant);
		modelMapper.map(restaurant, restaurantSummaryDto);

		restaurantSummaryDto.add(algaLinks.linkToRestaurants("restaurants"));
		restaurantSummaryDto.getKitchen().add(algaLinks.linkToKitchen(restaurantSummaryDto.getKitchen().getId()));

		return restaurantSummaryDto;
	}

}
