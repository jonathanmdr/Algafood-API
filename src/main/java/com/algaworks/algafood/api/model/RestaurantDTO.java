package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class RestaurantDTO {
	
	private Long id;
	private String name;
	private BigDecimal freightRate;
	private KitchenDTO kitchen;
	private Boolean active;

}
