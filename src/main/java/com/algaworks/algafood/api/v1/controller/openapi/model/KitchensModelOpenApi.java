package com.algaworks.algafood.api.v1.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.KitchenDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class KitchensModelOpenApi {

	private KitchenEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;

	@ApiModel("CozinhasEmbeddedModel")
	@Getter
	@Setter
	private class KitchenEmbeddedModelOpenApi {

		private List<KitchenDTO> kitchens;

	}

}
