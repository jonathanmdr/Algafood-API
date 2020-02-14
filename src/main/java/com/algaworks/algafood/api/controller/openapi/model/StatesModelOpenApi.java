package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.StateDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EstadosModel")
@Getter
@Setter
public class StatesModelOpenApi {

	private StateEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("EstadosEmbeddedModel")
	@Getter
	@Setter
	private class StateEmbeddedModelOpenApi {

		private List<StateDTO> states;

	}

}
