package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Item do Pedido", description = "Representação de um item do pedido")
@Getter
@Setter
public class OrderItemInput {
	
	@ApiModelProperty(example = "1", required = true, position = 10)
	@NotNull
	private Long productId;
	
	@ApiModelProperty(example = "2", required = true, position = 20)
	@NotNull
	@Min(value = 1)
	private Integer amount;
	
	@ApiModelProperty(example = "Sem palmito", required = true, position = 30)
	private String observation;

}
