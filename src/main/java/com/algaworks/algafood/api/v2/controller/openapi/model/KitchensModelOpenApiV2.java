package com.algaworks.algafood.api.v2.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.KitchenDTOV2;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class KitchensModelOpenApiV2 {

	private KitchenEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApiV2 page;

	@ApiModel("CozinhasEmbeddedModel")
	@Getter
	@Setter
	private class KitchenEmbeddedModelOpenApi {

		private List<KitchenDTOV2> kitchens;

	}

}
