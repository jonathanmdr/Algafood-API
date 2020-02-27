package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestaurantController;
import com.algaworks.algafood.api.v1.model.RestaurantJustNameDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantJustNameMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantJustNameDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public RestaurantJustNameMapper() {
		super(RestaurantController.class, RestaurantJustNameDTO.class);
	}

	@Override
	public RestaurantJustNameDTO toModel(Restaurant restaurant) {
		RestaurantJustNameDTO restaurantJustNameDto = createModelWithId(restaurant.getId(), restaurant);
		modelMapper.map(restaurant, restaurantJustNameDto);

		if (algaSecurity.canConsultingRestaurants()) {
		    restaurantJustNameDto.add(algaLinks.linkToRestaurants("restaurants"));
		}

		return restaurantJustNameDto;
	}

	@Override
	public CollectionModel<RestaurantJustNameDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
	    CollectionModel<RestaurantJustNameDTO> restaurantsModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.canConsultingRestaurants()) {
	        restaurantsModel.add(algaLinks.linkToRestaurants());
	    }
	    
	    return restaurantsModel;
	}

}
