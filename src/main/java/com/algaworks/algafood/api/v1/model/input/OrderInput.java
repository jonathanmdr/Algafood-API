package com.algaworks.algafood.api.v1.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "PedidoInput", description = "Representação de pedido")
@Getter
@Setter
public class OrderInput {
	
	@ApiModelProperty(required = true, position = 10)
	@Valid
	@NotNull	
	private RestaurantIdInput restaurant;
	
	@ApiModelProperty(required = true, position = 20)
	@Valid
	@NotNull
	private PaymentFormIdInput paymentForm;
	
	@ApiModelProperty(required = true, position = 30)
	@Valid
	@NotNull
	private AddressInput address;
	
	@ApiModelProperty(required = true, position = 40)
	@Valid
	@NotNull
	@Size(min = 1)
	private List<OrderItemInput> items;

}
