package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "states")
@ApiModel(value = "EstadoDTO", description = "Representação de UF")
@Getter
@Setter
public class StateDTO extends RepresentationModel<StateDTO> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "São Paulo")
	private String name;

}
