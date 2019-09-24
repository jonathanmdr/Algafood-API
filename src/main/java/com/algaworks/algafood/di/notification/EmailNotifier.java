package com.algaworks.algafood.di.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;

@NotifierType(PriorityType.MINIMUN)
@Component
public class EmailNotifier implements Notifier {
	
	@Override
	public void notify(Customer customer, String message) {
		System.out.printf("Notificando %s através do e-mail %s: %s\n",
				customer.getName(), customer.getEmail(), message);
	}

}
