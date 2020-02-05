package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.model.OrderDTO;
import com.algaworks.algafood.api.model.input.OrderInput;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderMapper extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public OrderMapper() {
		super(OrderController.class, OrderDTO.class);
	}

	@Override
	public OrderDTO toModel(Order order) {
		OrderDTO orderDto = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderDto);
		
		orderDto.add(algaLinks.linkToOrders());
		
		orderDto.getRestaurant()
		    .add(algaLinks.linkToRestaurant(orderDto.getRestaurant().getId()));
		
		orderDto.getCustomer()
		    .add(algaLinks.linkToUser(orderDto.getCustomer().getId()));
		
		orderDto.getPaymentForm()
		    .add(algaLinks.linkToPaymentForm(orderDto.getPaymentForm().getId()));
		
		orderDto.getAddress().getCity()
		    .add(algaLinks.linkToCity(orderDto.getAddress().getCity().getId()));
		
		orderDto.getItems().forEach(item -> {
			item.add(algaLinks.linkToProduct(orderDto.getRestaurant().getId(), item.getProductId(), "product"));
		});
		
		return orderDto;
	}

	public Order toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, Order.class);
	}

	public void copyToDomainObject(OrderInput orderInput, Order order) {
		modelMapper.map(orderInput, order);
	}

}
