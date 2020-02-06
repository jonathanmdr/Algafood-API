package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.api.model.input.RestaurantInput;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public RestaurantMapper() {
		super(RestaurantController.class, RestaurantDTO.class);
	}

	@Override
	public RestaurantDTO toModel(Restaurant restaurant) {
		RestaurantDTO restaurantDto = createModelWithId(restaurant.getId(), restaurant);
		modelMapper.map(restaurant, restaurantDto);

		if (restaurant.allowedActivate()) {
			restaurantDto.add(algaLinks.linkToRestaurantActivation(restaurantDto.getId(), "activate"));
		}

		if (restaurant.allowedInactivate()) {
			restaurantDto.add(algaLinks.linkToRestaurantInactivation(restaurantDto.getId(), "inactivate"));
		}

		if (restaurant.allowedOpening()) {
			restaurantDto.add(algaLinks.linkToRestaurantOpening(restaurantDto.getId(), "opening"));
		}

		if (restaurant.allowedClosing()) {
			restaurantDto.add(algaLinks.linkToRestaurantClosing(restaurantDto.getId(), "closing"));
		}

		restaurantDto.add(algaLinks.linkToRestaurants("restaurants"));

		restaurantDto.getKitchen().add(algaLinks.linkToKitchen(restaurantDto.getKitchen().getId()));

		restaurantDto.getAddress().getCity().add(algaLinks.linkToCity(restaurantDto.getAddress().getCity().getId()));

		restaurantDto.add(algaLinks.linkToRestaurantPaymentForms(restaurantDto.getId(), "payment-forms"));

		restaurantDto.add(algaLinks.linkToRestaurantUserManager(restaurantDto.getId(), "responsible-users"));

		return restaurantDto;
	}

	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);
	}

	public RestaurantInput toInputObject(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantInput.class);
	}

	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		/*
		 * Para evitar a exception: org.hibernate.HibernateException: identifier of an
		 * instance of com.algaworks.algafood.domain.model.Kitchen was altered from 1 to
		 * 2
		 */
		restaurant.setKitchen(new Kitchen());

		/*
		 * Para evitar a exception: org.hibernate.HibernateException: identifier of an
		 * instance of com.algaworks.algafood.domain.model.City was altered from 1 to 2
		 */
		if (restaurant.getAddress() != null) {
			restaurant.getAddress().setCity(new City());
		}

		modelMapper.map(restaurantInput, restaurant);
	}

}
