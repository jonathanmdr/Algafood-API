package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.OrderInput;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.OrderController;
import com.algaworks.algafood.api.v1.model.OrderDTO;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderMapper extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public OrderMapper() {
		super(OrderController.class, OrderDTO.class);
	}

	@Override
	public OrderDTO toModel(Order order) {
		OrderDTO orderDto = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderDto);
		
		orderDto.add(algaLinks.linkToOrders("orders"));
		
		if (algaSecurity.canManageOrders(order.getCode())) {
		    if (order.canBeConfirmed()) {
                orderDto.add(algaLinks.linkToConfirmOrder(orderDto.getCode(), "confirm"));
		    }
		
		    if (order.canBeCanceled()) {
                orderDto.add(algaLinks.linkToCancelOrder(orderDto.getCode(), "cancel"));
		    }
		
		    if (order.canBeDelivered()) {
                orderDto.add(algaLinks.linkToDeliverOrder(orderDto.getCode(), "deliver"));
		    }
		}
		
		if (algaSecurity.canConsultingRestaurants()) {
		    orderDto.getRestaurant()
		        .add(algaLinks.linkToRestaurant(orderDto.getRestaurant().getId()));
		}
		
		if (algaSecurity.canConsultingUsersGroupsPermissions()) {
		    orderDto.getCustomer()
		        .add(algaLinks.linkToUser(orderDto.getCustomer().getId()));
		}
		
		if (algaSecurity.canConsultingPaymentForms()) {
		    orderDto.getPaymentForm()
		        .add(algaLinks.linkToPaymentForm(orderDto.getPaymentForm().getId()));
		}
		
		if (algaSecurity.canConsultingCities()) {
		    orderDto.getAddress().getCity()
		        .add(algaLinks.linkToCity(orderDto.getAddress().getCity().getId()));
		}
		
		/**
		 * Usuário com permissão de consultar restaurantes também pode consultar os produtos do mesmo.
		 */
		if (algaSecurity.canConsultingRestaurants()) {
		    orderDto.getItems().forEach(item -> {
			    item.add(algaLinks.linkToProduct(orderDto.getRestaurant().getId(), item.getProductId(), "product"));
		    });
		}
		
		return orderDto;
	}

	public Order toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, Order.class);
	}

	public void copyToDomainObject(OrderInput orderInput, Order order) {
		modelMapper.map(orderInput, order);
	}

}
