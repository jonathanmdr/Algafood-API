package com.algaworks.algafood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public CityNotFoundException(String message) {
		super(message);
	}
	
	public CityNotFoundException(Long stateId) {
		this(String.format("Cidade de ID: %d não existe!", stateId));
	}

}
