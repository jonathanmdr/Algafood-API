package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.RestaurantUserManagerControllerOpenApi;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.mapper.UserMapper;
import com.algaworks.algafood.api.v1.model.UserSummaryDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserManagerController implements RestaurantUserManagerControllerOpenApi {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@Override
	@Security.Restaurants.AllowedConsult
	@GetMapping
	public CollectionModel<UserSummaryDTO> findAllByRestaurantId(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		CollectionModel<UserSummaryDTO> userSummaryDto = userMapper.toCollectionModel(restaurant.getUsers())
				.removeLinks();
		
		if (algaSecurity.canEditingUsersGroupsPermissions()) {
		    userSummaryDto.add(algaLinks.linkToRestaurantUserManager(restaurant.getId()))
				.add(algaLinks.linkToRestaurantUserManagerAssociate(restaurantId, "associate"));

		    userSummaryDto.getContent().forEach(user -> {
			    user.add(algaLinks.linkToRestaurantUserManagerDisassociate(restaurantId, user.getId(), "disassociate"));
		    });
		}

		return userSummaryDto;
	}

	@Override
	@Security.Restaurants.AllowedEdit
	@PutMapping("/{userId}")
	public ResponseEntity<Void> associateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantService.associateUser(restaurantId, userId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@Security.Restaurants.AllowedEdit
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantService.disassociateUser(restaurantId, userId);

		return ResponseEntity.noContent().build();
	}

}
