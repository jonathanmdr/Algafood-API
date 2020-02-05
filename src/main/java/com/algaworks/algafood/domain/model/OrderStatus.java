package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum OrderStatus {
	
	CREATED("Criado"),
	CONFIRMED("Confirmado", CREATED),
	DELIVERED("Entregue", CONFIRMED),
	CANCELED("Cancelado", CREATED);
	
	private String description;
	private List<OrderStatus> previousStatuses;
	
	OrderStatus(String description, OrderStatus ... previousStatuses) {
		this.description = description;
		this.previousStatuses = Arrays.asList(previousStatuses);
	}
	
	public boolean cannotChangeTo(OrderStatus newStatus) {
		return !newStatus.previousStatuses.contains(this);
	}
	
	public boolean canChangeTo(OrderStatus newStatus) {
		return !cannotChangeTo(newStatus);
	}

}
