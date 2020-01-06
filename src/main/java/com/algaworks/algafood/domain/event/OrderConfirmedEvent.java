package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {

	private Order order;
	
}
