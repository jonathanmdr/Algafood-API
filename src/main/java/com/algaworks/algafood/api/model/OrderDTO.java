package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Pedido Completo", description = "Representação de um pedido detalhado")
@Getter
@Setter
public class OrderDTO {
	
	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55", position = 10)
	private String code;
	
	@ApiModelProperty(example = "30.00", position = 20)
	private BigDecimal subTotal;
	
	@ApiModelProperty(example = "8.00", position = 30)
	private BigDecimal freightRate;
	
	@ApiModelProperty(example = "38.00", position = 40)
	private BigDecimal amount;
	
	@ApiModelProperty(example = "DELIVERED", position = 50)
	private String status;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z", position = 60)
	private OffsetDateTime creationDate;
	
	@ApiModelProperty(example = "2019-12-01T20:35:04Z", position = 70)
	private OffsetDateTime confirmatedDate;
	
	@ApiModelProperty(position = 80)
	private OffsetDateTime canceledDate;
	
	@ApiModelProperty(example = "2019-12-01T21:34:04Z", position = 90)
	private OffsetDateTime deliveredDate;
	
	@ApiModelProperty(position = 100)
	private AddressDTO address;
	
	@ApiModelProperty(position = 110)
	private PaymentFormDTO paymentForm;
	
	@ApiModelProperty(position = 120)
	private RestaurantSummaryDTO restaurant;
	
	@ApiModelProperty(position = 130)
	private UserSummaryDTO customer;
	
	@ApiModelProperty(position = 140)
	private List<OrderItemSummaryDTO> items;

}
