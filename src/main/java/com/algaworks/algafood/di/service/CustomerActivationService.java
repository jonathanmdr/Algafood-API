package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;
import com.algaworks.algafood.di.notification.Notifier;
import com.algaworks.algafood.di.notification.NotifierType;
import com.algaworks.algafood.di.notification.PriorityType;

@Component
public class CustomerActivationService {
	
	@NotifierType(PriorityType.MINIMUN)
	@Autowired
	private Notifier notifier;
	
	public void activate(Customer customer) {
		customer.activate(true);
		
		notifier.notify(customer, "Seu cadastro está ativo!");
	}

}
