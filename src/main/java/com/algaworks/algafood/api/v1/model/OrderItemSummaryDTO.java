package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "order-items")
@ApiModel(value = "ItemDoPedidoResumoDTO", description = "Representação resumida de um item do pedido")
@Getter
@Setter
public class OrderItemSummaryDTO extends RepresentationModel<OrderItemSummaryDTO> {

	@ApiModelProperty(example = "1", position = 10)
	private Long productId;

	@ApiModelProperty(example = "Pizza média 10 pedaços", position = 20)
	private String productName;

	@ApiModelProperty(example = "30.00", position = 30)
	private BigDecimal unitPrice;

	@ApiModelProperty(example = "30.00", position = 40)
	private BigDecimal totalPrice;

	@ApiModelProperty(example = "1", position = 50)
	private Integer amount;

	@ApiModelProperty(example = "Sem palmito", position = 60)
	private String observation;

}
