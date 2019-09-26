package com.algaworks.algafood.di.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Customer;

@NotifierType(PriorityType.MINIMUN)
@Component
public class EmailNotifier implements Notifier {
		
	@Autowired
	private NotifierProperties properties;
	
	@Override
	public void notify(Customer customer, String message) {
		System.out.println("Host:" + properties.getHostServer());
		System.out.println("Port:" + properties.getPortServer());
		System.out.printf("Notificando %s através do e-mail %s: %s\n",
				customer.getName(), customer.getEmail(), message);
	}

}
