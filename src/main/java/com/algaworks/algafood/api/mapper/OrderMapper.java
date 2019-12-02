package com.algaworks.algafood.api.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.OrderDTO;
import com.algaworks.algafood.api.model.OrderSummaryDTO;
import com.algaworks.algafood.api.model.input.OrderInput;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderDTO toDto(Order order) {
		return modelMapper.map(order, OrderDTO.class);
	}
	
	public OrderSummaryDTO toSummaryDto(Order order) {
		return modelMapper.map(order, OrderSummaryDTO.class);
	}
	
	public List<OrderSummaryDTO> toCollectionDto(Collection<Order> orders) {
		return orders.stream().map(order -> toSummaryDto(order)).collect(Collectors.toList());
	}
	
	public Order toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, Order.class);
	}
	
	public void copyToDomainObject(OrderInput orderInput, Order order) {
		modelMapper.map(orderInput, order);
	}

}
