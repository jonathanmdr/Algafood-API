package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemSummaryDTO {
	
	private Long productId;
	private String productName;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private Integer amount;
	private String observation;

}
