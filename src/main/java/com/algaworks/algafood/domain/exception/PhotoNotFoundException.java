package com.algaworks.algafood.domain.exception;

public class PhotoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public PhotoNotFoundException(String message) {
		super(message);
	}
	
	public PhotoNotFoundException(Long restaurantId, Long productId) {
		this(String.format("Não existe um cadastro de foto de produto com ID: %d para o restaurante de ID: %d.", productId, restaurantId));
	}

}
