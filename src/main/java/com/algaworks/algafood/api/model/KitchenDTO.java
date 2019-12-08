package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenDTO {
	
	@JsonView(RestaurantView.Summary.class)
	private Long id;
	
	@JsonView(RestaurantView.Summary.class)
	private String name;

}
