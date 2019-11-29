package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	
	private Long id;
	private BigDecimal subTotal;
	private BigDecimal freightRate;
	private BigDecimal amount;
	private OffsetDateTime creationDate;
	private OffsetDateTime confirmatedDate;
	private OffsetDateTime canceledDate;
	private OffsetDateTime deliveredDate;
	private AddressDTO address;
	private PaymentFormDTO paymentForm;
	private RestaurantSummaryDTO restaurant;
	private UserSummaryDTO customer;
	private List<OrderItemSummaryDTO> items;

}
