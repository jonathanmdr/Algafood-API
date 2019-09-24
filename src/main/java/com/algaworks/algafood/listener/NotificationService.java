package com.algaworks.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.notification.Notifier;
import com.algaworks.algafood.di.notification.NotifierType;
import com.algaworks.algafood.di.notification.PriorityType;
import com.algaworks.algafood.di.service.CustomerActiveEvent;

@Component
public class NotificationService {
	
	@NotifierType(PriorityType.MINIMUN)
	@Autowired
	private Notifier notifier;

	@EventListener
	public void customerActiveListner(CustomerActiveEvent event) {
		notifier.notify(event.getCustomer(), "Seu cadastro no sistema está ativo!");
	}
	
}
