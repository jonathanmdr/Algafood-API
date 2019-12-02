package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Order;

@Service
public class OrderStatusService {		
	
	@Autowired
	private OrderService orderService;
	
	@Transactional
	public void confirm(Long orderId) {
		Order order = orderService.findById(orderId);
		order.confirm();
	}
	
	@Transactional
	public void deliver(Long orderId) {
		Order order = orderService.findById(orderId);
		order.deliver();
	}
	
	@Transactional
	public void cancel(Long orderId) {
		Order order = orderService.findById(orderId);
		order.cancel();
	}

}
