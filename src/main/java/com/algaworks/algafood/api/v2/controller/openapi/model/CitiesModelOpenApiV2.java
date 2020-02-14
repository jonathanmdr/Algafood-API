package com.algaworks.algafood.api.v2.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.CityDTOV2;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadesModel")
@Getter
@Setter
public class CitiesModelOpenApiV2 {

	private CityEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("CidadesEmbeddedModel")
	@Getter
	@Setter
	private class CityEmbeddedModelOpenApi {

		private List<CityDTOV2> cities;

	}

}
