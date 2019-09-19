package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.di.model.Customer;
import com.algaworks.algafood.di.notification.Notifier;

@Service
public class CustomerActivationService {
	
	@Autowired
	private Notifier notifier;
	
	private void activate(Customer customer) {
		customer.activate();
		
		notifier.notify(customer, "Seu cadastro está ativo!");
	}

}
