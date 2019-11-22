package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantInput {
	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal freightRate;
	
	@Valid
	@NotNull
	private KitchenIdInput kitchen;

}
