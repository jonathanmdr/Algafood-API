package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.model.OrderSummaryDTO;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderSummaryMapper extends RepresentationModelAssemblerSupport<Order, OrderSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public OrderSummaryMapper() {
		super(OrderController.class, OrderSummaryDTO.class);
	}

	@Override
	public OrderSummaryDTO toModel(Order order) {
		OrderSummaryDTO orderSummaryDto = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderSummaryDto);

		orderSummaryDto.add(algaLinks.linkToOrders());
		orderSummaryDto.getRestaurant().add(algaLinks.linkToRestaurant(orderSummaryDto.getRestaurant().getId()));
		orderSummaryDto.getCustomer().add(algaLinks.linkToUser(orderSummaryDto.getCustomer().getId()));

		return orderSummaryDto;
	}

}
