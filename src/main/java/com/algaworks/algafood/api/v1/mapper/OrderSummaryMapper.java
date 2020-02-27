package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.OrderController;
import com.algaworks.algafood.api.v1.model.OrderSummaryDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderSummaryMapper extends RepresentationModelAssemblerSupport<Order, OrderSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public OrderSummaryMapper() {
		super(OrderController.class, OrderSummaryDTO.class);
	}

	@Override
	public OrderSummaryDTO toModel(Order order) {
		OrderSummaryDTO orderSummaryDto = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderSummaryDto);

		if (algaSecurity.canConsultingOrders()) {
		    orderSummaryDto.add(algaLinks.linkToOrders("orders"));
		}
		
		if (algaSecurity.canConsultingRestaurants()) {
		    orderSummaryDto.getRestaurant().add(algaLinks.linkToRestaurant(orderSummaryDto.getRestaurant().getId()));
		}
		
		if (algaSecurity.canConsultingUsersGroupsPermissions()) {
		    orderSummaryDto.getCustomer().add(algaLinks.linkToUser(orderSummaryDto.getCustomer().getId()));
		}

		return orderSummaryDto;
	}

}
