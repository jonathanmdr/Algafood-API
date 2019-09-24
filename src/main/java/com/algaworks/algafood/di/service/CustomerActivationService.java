package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;

@Component
public class CustomerActivationService {
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void activate(Customer customer) {
		customer.activate(Boolean.TRUE);
		applicationEventPublisher.publishEvent(new CustomerActiveEvent(customer));
	}

}
