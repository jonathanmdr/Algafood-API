package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.OrderStatusService;

@RestController
@RequestMapping("/orders/{orderId}")
public class OrderStatusController {
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@PutMapping("/confirm")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable Long orderId) {
		orderStatusService.confirm(orderId);
	}
	
	@PutMapping("/deliver")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable Long orderId) {
		orderStatusService.deliver(orderId);
	}
	
	@PutMapping("/cancel")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable Long orderId) {
		orderStatusService.cancel(orderId);
	}

}
