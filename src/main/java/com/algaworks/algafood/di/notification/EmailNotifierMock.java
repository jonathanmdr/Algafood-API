package com.algaworks.algafood.di.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;

@Profile("dev")
@NotifierType(PriorityType.MINIMUN)
@Component
public class EmailNotifierMock implements Notifier {
	
	public EmailNotifierMock() {
		System.out.println("Notificador MOCK");
	}
	
	@Override
	public void notify(Customer customer, String message) {
		System.out.printf("MOCK: Notificação seria enviada para %s através do e-mail %s: %s\n",
				customer.getName(), customer.getEmail(), message);
	}

}
