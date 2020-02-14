package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "paymentForms")
@ApiModel(value = "FormaDePagamentoDTO", description = "Representação de forma de pagamento")
@Getter
@Setter
public class PaymentFormDTO extends RepresentationModel<PaymentFormDTO> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Cartão de crédito")
	private String name;

}
