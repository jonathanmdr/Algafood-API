package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "orders")
@Getter
@Setter
public class OrderSummaryDTO extends RepresentationModel<OrderSummaryDTO> {
	
	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55", position = 10)
	private String code;
	
	@ApiModelProperty(example = "298.90", position = 20)
	private BigDecimal subTotal;
	
	@ApiModelProperty(example = "10.00", position = 30)
	private BigDecimal freightRate;
	
	@ApiModelProperty(example = "308.90", position = 40)
	private BigDecimal amount;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z", position = 50)
	private OffsetDateTime creationDate;
	
	@ApiModelProperty(position = 60)
	private RestaurantJustNameDTO restaurant;
	
	@ApiModelProperty(position = 70)
	private UserSummaryDTO customer;

}
