package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "groups")
@ApiModel(value = "GrupoDTO", description = "Representação de grupo de usuário")
@Getter
@Setter
public class GroupDTO extends RepresentationModel<GroupDTO> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Administrador")
	private String name;

}
