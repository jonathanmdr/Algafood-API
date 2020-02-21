package com.algaworks.algafood.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.OrderControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.OrderInput;
import com.algaworks.algafood.api.v1.mapper.OrderMapper;
import com.algaworks.algafood.api.v1.mapper.OrderSummaryMapper;
import com.algaworks.algafood.api.v1.model.OrderDTO;
import com.algaworks.algafood.api.v1.model.OrderSummaryDTO;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.OrderService;

@RestController
@RequestMapping(path = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerOpenApi {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderSummaryMapper orderSummaryMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private PagedResourcesAssembler<Order> pagedResourceAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@Override
	@Security.Orders.AllowedConsultAll
	@GetMapping
	public PagedModel<OrderSummaryDTO> findAll(OrderFilter orderFilter, @PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTransalated = translatePageable(pageable);
		Page<Order> orders = orderService.findAll(orderFilter, pageableTransalated);
		
		orders = new PageWrapper<>(orders, pageable);
		
		return pagedResourceAssembler.toModel(orders, orderSummaryMapper);
	}

	@Override
	@Security.Orders.AllowedConsultUnique
	@GetMapping("/{code}")
	public OrderDTO findById(@PathVariable String code) {
		return orderMapper.toModel(orderService.findByCode(code));
	}

	@Override
	@Security.Orders.AllowedEdit
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO save(@RequestBody @Valid OrderInput orderInput) {
		try {
			Order order = orderMapper.toDomainObject(orderInput);

			order.setCustomer(new User());
			order.getCustomer().setId(algaSecurity.getUserId());

			return orderMapper.toModel(orderService.save(order));
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	private Pageable translatePageable(Pageable pageable) {
		var mapping = Map.of("code", "code", 
				"subTotal", "subTotal", 
				"freightRate", "freightRate", 
				"amount", "amount",
				"creationDate", "creationDate", 
				"restaurant.id", "restaurant.id", 
				"restaurant.name", "restaurant.name",
				"customer.id", "customer.id", 
				"customer.name", "customer.name");

		return PageableTranslator.translate(pageable, mapping);
	}

}
