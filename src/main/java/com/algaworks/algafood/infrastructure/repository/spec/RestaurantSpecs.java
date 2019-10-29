package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurant;

public class RestaurantSpecs {
	
	public static Specification<Restaurant> withFreightRate() {
		return (root, query, builder) -> builder.equal(root.get("freightRate"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurant> withNameLike(String restaurantName) {
		return (root, query, builder) -> builder.like(root.get("name"), "%"+ restaurantName + "%");
	}

}
