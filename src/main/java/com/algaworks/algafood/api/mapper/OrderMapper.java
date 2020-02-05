package com.algaworks.algafood.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CityController;
import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.controller.PaymentFormController;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.controller.RestaurantProductController;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.model.OrderDTO;
import com.algaworks.algafood.api.model.input.OrderInput;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderMapper extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public OrderMapper() {
		super(OrderController.class, OrderDTO.class);
	}

	@Override
	public OrderDTO toModel(Order order) {
		OrderDTO orderDto = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderDto);
		
		orderDto.add(linkTo(OrderController.class).withRel("orders"));
		
		orderDto.getRestaurant().add(linkTo(methodOn(RestaurantController.class)
				.findById(orderDto.getRestaurant().getId())).withSelfRel());
		
		orderDto.getCustomer().add(linkTo(methodOn(UserController.class)
				.findById(orderDto.getCustomer().getId())).withSelfRel());
		
		// Passamos null no segundo parâmetro, porquê para a construção da URL do recurso
		// de forma de pagamento o mesmo é indiferente.
		orderDto.getPaymentForm().add(linkTo(methodOn(PaymentFormController.class)
				.findById(orderDto.getPaymentForm().getId(), null)).withSelfRel());
		
		orderDto.getAddress().getCity().add(linkTo(methodOn(CityController.class)
				.findById(orderDto.getAddress().getCity().getId())).withSelfRel());
		
		orderDto.getItems().forEach(item -> {
			item.add(linkTo(methodOn(RestaurantProductController.class)
					.findById(orderDto.getRestaurant().getId(), item.getProductId())).withRel("product"));
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
