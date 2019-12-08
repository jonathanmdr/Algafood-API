package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class RestaurantDTO {
	
	@JsonView({ RestaurantView.Summary.class, RestaurantView.JustName.class })
	private Long id;
	
	@JsonView({ RestaurantView.Summary.class, RestaurantView.JustName.class })
	private String name;
	
	@JsonView(RestaurantView.Summary.class)
	private BigDecimal freightRate;
	
	@JsonView(RestaurantView.Summary.class)
	private KitchenDTO kitchen;
	
	private Boolean active;
	private Boolean opened;
	private AddressDTO address;

}
