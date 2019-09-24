package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.model.Customer;

public class CustomerActiveEvent {
	
	private Customer customer;
	
	public CustomerActiveEvent(Customer customer) {
		super();
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}		

}
