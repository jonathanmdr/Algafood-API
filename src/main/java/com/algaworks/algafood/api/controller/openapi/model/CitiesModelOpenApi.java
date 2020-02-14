package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CityDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadesModel")
@Getter
@Setter
public class CitiesModelOpenApi {

	private CityEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("CidadesEmbeddedModel")
	@Getter
	@Setter
	private class CityEmbeddedModelOpenApi {

		private List<CityDTO> cities;

	}

}
