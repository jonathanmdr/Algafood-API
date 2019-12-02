package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInput {
	
	@Valid
	@NotNull	
	private RestaurantIdInput restaurant;
	
	@Valid
	@NotNull
	private PaymentFormIdInput paymentForm;
	
	@Valid
	@NotNull
	private AddressInput address;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<OrderItemInput> items;

}
