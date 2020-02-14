package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.UserSummaryDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("UsuariosModel")
@Getter
@Setter
public class UsersSummaryModelOpenApi {

	private UserSummaryEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("UsuariosEmbeddedModel")
	@Getter
	@Setter
	private class UserSummaryEmbeddedModelOpenApi {

		private List<UserSummaryDTO> users;

	}

}
