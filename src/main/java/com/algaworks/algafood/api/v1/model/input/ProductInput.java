package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {
	
	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal price;
	
	@NotNull
	private Boolean active;

}
