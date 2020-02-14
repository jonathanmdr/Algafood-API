package com.algaworks.algafood.api.v1.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.RestaurantSummaryDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteModel")
@Getter
@Setter
public class RestaurantsSummaryModelOpenApi {

	private RestaurantSummaryEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("RestauranteEmbeddedModel")
	@Getter
	@Setter
	private class RestaurantSummaryEmbeddedModelOpenApi {

		private List<RestaurantSummaryDTO> restaurants;

	}

}
