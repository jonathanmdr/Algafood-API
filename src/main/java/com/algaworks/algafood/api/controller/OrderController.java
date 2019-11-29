package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.OrderMapper;
import com.algaworks.algafood.api.model.OrderDTO;
import com.algaworks.algafood.api.model.OrderSummaryDTO;
import com.algaworks.algafood.domain.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@GetMapping
	public List<OrderSummaryDTO> findAll() {
		return orderMapper.toCollectionDto(orderService.findAll());
	}
	
	@GetMapping("/{orderId}")
	public OrderDTO findById(@PathVariable Long orderId) {
		return orderMapper.toDto(orderService.findById(orderId));
	}

}
