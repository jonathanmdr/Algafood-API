package com.algaworks.algafood.domain.exception;

public class PaymentFormNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public PaymentFormNotFoundException(String message) {
		super(message);
	}
	
	public PaymentFormNotFoundException(Long paymentFormId) {
		this(String.format("Forma de pagamento de ID: %d não existe!", paymentFormId));
	}

}
