package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.OrderMapper;
import com.algaworks.algafood.api.model.OrderDTO;
import com.algaworks.algafood.api.model.OrderSummaryDTO;
import com.algaworks.algafood.api.model.input.OrderInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
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
	
	@GetMapping("/{code}")
	public OrderDTO findById(@PathVariable String code) {
		return orderMapper.toDto(orderService.findByCode(code));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO save(@RequestBody @Valid OrderInput orderInput) {
		try {
			Order order = orderMapper.toDomainObject(orderInput);
			
			// TODO: Alterar para pegar o usuário logado
			order.setCustomer(new User());
			order.getCustomer().setId(1L);
			
			return orderMapper.toDto(orderService.save(order));
		} catch(EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

}
