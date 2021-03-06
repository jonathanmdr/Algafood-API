package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "kitchens")
@ApiModel(value = "CozinhaDTO", description = "Representação de cozinha")
@Getter
@Setter
public class KitchenDTO extends RepresentationModel<KitchenDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cozinha americana")
	private String name;

}
