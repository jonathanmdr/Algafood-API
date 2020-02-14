package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.OrderStatusControllerOpenApi;
import com.algaworks.algafood.domain.service.OrderStatusService;

@RestController
@RequestMapping(path = "/orders/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderStatusController implements OrderStatusControllerOpenApi {
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@PutMapping("/confirm")	
	public ResponseEntity<Void> confirm(@PathVariable String code) {
		orderStatusService.confirm(code);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/deliver")	
	public ResponseEntity<Void> deliver(@PathVariable String code) {
		orderStatusService.deliver(code);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cancel")
	public ResponseEntity<Void> cancel(@PathVariable String code) {
		orderStatusService.cancel(code);
		return ResponseEntity.noContent().build();
	}

}
