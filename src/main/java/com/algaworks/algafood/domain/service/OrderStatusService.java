package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.OrderRepository;

@Service
public class OrderStatusService {		
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Transactional
	public void confirm(String code) {
		Order order = orderService.findByCode(code);
		order.confirm();
		
		orderRepository.save(order);
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
		
		orderRepository.save(order);
	}

}
