package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "permissions")
@ApiModel(value = "Permissão", description = "Representação de permissão")
@Getter
@Setter
public class PermissionDTO extends RepresentationModel<PermissionDTO> {

	@ApiModelProperty(example = "1", position = 10)
	private Long id;

	@ApiModelProperty(example = "CONSULTAR_COZINHAS", position = 20)
	private String name;

	@ApiModelProperty(example = "Permite consultar cozinhas", position = 30)
	private String description;

}
