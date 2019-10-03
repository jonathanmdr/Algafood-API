package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.PaymentForm;

public interface PaymentFormRepository {
	
	List<PaymentForm> list();
	
	PaymentForm findById(Long id);
	
	PaymentForm save(PaymentForm paymentForm);
	
	void delete(PaymentForm paymentForm);

}
