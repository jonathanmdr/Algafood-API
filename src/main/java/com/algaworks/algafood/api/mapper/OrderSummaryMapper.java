package com.algaworks.algafood.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.model.OrderSummaryDTO;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderSummaryMapper extends RepresentationModelAssemblerSupport<Order, OrderSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public OrderSummaryMapper() {
		super(OrderController.class, OrderSummaryDTO.class);
	}

	@Override
	public OrderSummaryDTO toModel(Order order) {
		OrderSummaryDTO orderSummaryDto = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderSummaryDto);

		orderSummaryDto.add(linkTo(OrderController.class).withRel("orders"));
		orderSummaryDto.getRestaurant().add(linkTo(methodOn(RestaurantController.class)
				.findById(orderSummaryDto.getRestaurant().getId())).withSelfRel());
		orderSummaryDto.getCustomer().add(linkTo(methodOn(UserController.class)
				.findById(orderSummaryDto.getCustomer().getId())).withSelfRel());

		return orderSummaryDto;
	}

}
