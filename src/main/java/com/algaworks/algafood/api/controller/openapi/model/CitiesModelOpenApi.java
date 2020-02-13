package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CityDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CitiesModelOpenApi {
	
	private CityEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	private class CityEmbeddedModelOpenApi {
		
		private List<CityDTO> cities;
		
	}

}
