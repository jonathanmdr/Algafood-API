package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.PermissionDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PermissaoModel")
@Getter
@Setter
public class PermissionsModelOpenApi {

	private PermissionEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("PermissaoEmbeddedModel")
	@Getter
	@Setter
	private class PermissionEmbeddedModelOpenApi {

		private List<PermissionDTO> permissions;

	}

}
