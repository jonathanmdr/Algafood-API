package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemInput {
	
	@NotNull
	private Long productId;
	
	@NotNull
	@Min(value = 1)
	private Integer amount;
	
	private String observation;

}
