package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.GroupDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("GruposModel")
@Getter
@Setter
public class GroupsModelOpenApi {

	private GroupEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("GruposEmbeddedModel")
	@Getter
	@Setter
	private class GroupEmbeddedModelOpenApi {

		private List<GroupDTO> groups;

	}

}
