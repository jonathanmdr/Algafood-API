package com.algaworks.algafood.domain.exception;

public class kitchenNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public kitchenNotFoundException(String message) {
		super(message);
	}
	
	public kitchenNotFoundException(Long stateId) {
		this(String.format("Cozinha de ID: %d não existe!", stateId));
	}

}
