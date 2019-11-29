package com.algaworks.algafood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(String message) {
		super(message);
	}
	
	public OrderNotFoundException(Long orderId) {
		this(String.format("Pedido de ID: %d não existe!", orderId));
	}

}
