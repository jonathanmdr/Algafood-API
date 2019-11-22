package com.algaworks.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class KitchenMixin {
	
	@JsonIgnore
	private List<Restaurant> restaurants = new ArrayList<>();

}
