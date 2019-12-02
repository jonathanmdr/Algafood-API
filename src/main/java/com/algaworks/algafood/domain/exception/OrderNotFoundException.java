package com.algaworks.algafood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(String code) {
		super(String.format("Pedido de ID: %s não existe!", code));
	}

}
