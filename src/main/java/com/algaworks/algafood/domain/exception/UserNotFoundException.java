package com.algaworks.algafood.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Long userId) {
		this(String.format("Usuário de ID: %d não existe!", userId));
	}

}
