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
	public void confirm(String code) {
		Order order = orderService.findByCode(code);
		order.confirm();
	}
	
	@Transactional
	public void deliver(String code) {
		Order order = orderService.findByCode(code);
		order.deliver();
	}
	
	@Transactional
	public void cancel(String code) {
		Order order = orderService.findByCode(code);
		order.cancel();
	}

}
