package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.OrderCanceledEvent;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.domain.service.SendEmailService.Message;

@Component
public class NotificationCustomerOrderCanceledListener {
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@TransactionalEventListener
	public void whenConfirmingOrder(OrderCanceledEvent event) {
		Order order = event.getOrder();
		
		var message = Message.builder()
				.subject(order.getRestaurant().getName() + " - Pedido cancelado")
				.body("emails/pedido-cancelado.html")
				.variable("order", order)
				.recipient(order.getCustomer().getEmail())
				.build();
		
		sendEmailService.send(message);		
	}

}
