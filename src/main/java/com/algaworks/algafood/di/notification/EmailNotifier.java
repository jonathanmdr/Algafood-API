package com.algaworks.algafood.di.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;

@Profile("prod")
@NotifierType(PriorityType.MINIMUN)
@Component
public class EmailNotifier implements Notifier {
	
	public EmailNotifier() {
		System.out.println("Notificador REAL");
	}
	
	@Override
	public void notify(Customer customer, String message) {
		System.out.printf("Notificando %s através do e-mail %s: %s\n",
				customer.getName(), customer.getEmail(), message);
	}

}
