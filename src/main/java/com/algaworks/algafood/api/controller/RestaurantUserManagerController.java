package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.openapi.controller.RestaurantUserManagerControllerOpenApi;
import com.algaworks.algafood.api.mapper.UserMapper;
import com.algaworks.algafood.api.model.UserSummaryDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserManagerController implements RestaurantUserManagerControllerOpenApi {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<UserSummaryDTO> findAllByRestaurantId(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		return userMapper.toCollectionModel(restaurant.getUsers())
				.removeLinks()
				.add(algaLinks.linkToRestaurantUserManager(restaurant.getId()));
	}

	@Override
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantService.associateUser(restaurantId, userId);
	}

	@Override
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantService.disassociateUser(restaurantId, userId);
	}

}
