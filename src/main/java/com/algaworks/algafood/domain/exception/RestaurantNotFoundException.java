package com.algaworks.algafood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public RestaurantNotFoundException(String message) {
		super(message);
	}
	
	public RestaurantNotFoundException(Long restaurantId) {
		this(String.format("Restaurante de ID: %d não existe!", restaurantId));
	}

}
