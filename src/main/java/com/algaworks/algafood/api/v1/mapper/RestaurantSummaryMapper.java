package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestaurantController;
import com.algaworks.algafood.api.v1.model.RestaurantSummaryDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantSummaryMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public RestaurantSummaryMapper() {
		super(RestaurantController.class, RestaurantSummaryDTO.class);
	}

	@Override
	public RestaurantSummaryDTO toModel(Restaurant restaurant) {
		RestaurantSummaryDTO restaurantSummaryDto = createModelWithId(restaurant.getId(), restaurant);
		modelMapper.map(restaurant, restaurantSummaryDto);

		if (algaSecurity.canConsultingRestaurants()) {
		    restaurantSummaryDto.add(algaLinks.linkToRestaurants("restaurants"));
		}
		
		if (algaSecurity.canConsultingKitchens()) {
		    restaurantSummaryDto.getKitchen().add(algaLinks.linkToKitchen(restaurantSummaryDto.getKitchen().getId()));
		}

		return restaurantSummaryDto;
	}

	@Override
	public CollectionModel<RestaurantSummaryDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
	    CollectionModel<RestaurantSummaryDTO> restaurantsModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.canConsultingRestaurants()) {
	        restaurantsModel.add(algaLinks.linkToRestaurants());
	    }
	    
	    return restaurantsModel;
	}

}
