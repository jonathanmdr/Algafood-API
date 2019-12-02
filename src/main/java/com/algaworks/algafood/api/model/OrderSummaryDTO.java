package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryDTO {
	
	private String code;
	private BigDecimal subTotal;
	private BigDecimal freightRate;
	private BigDecimal amount;
	private OffsetDateTime creationDate;
	private RestaurantSummaryDTO restaurant;
	private UserSummaryDTO customer;

}
