package com.algaworks.algafood.di.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;

@NotifierType(PriorityType.MAXIMUN)
@Component
public class SMSNotifier implements Notifier {
	
	@Override
	public void notify(Customer customer, String message) {
		System.out.printf("Notificando %s por SMS pelo telefone %s: %s\n",
				customer.getName(), customer.getPhone(), message);
	}

}
