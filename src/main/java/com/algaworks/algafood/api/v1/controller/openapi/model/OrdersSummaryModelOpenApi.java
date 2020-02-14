package com.algaworks.algafood.api.v1.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.OrderSummaryDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Getter
@Setter
public class OrdersSummaryModelOpenApi {

	private OrderSummaryEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;

	@ApiModel("PedidosEmbeddedModel")
	@Getter
	@Setter
	private class OrderSummaryEmbeddedModelOpenApi {

		private List<OrderSummaryDTO> orders;

	}

}
