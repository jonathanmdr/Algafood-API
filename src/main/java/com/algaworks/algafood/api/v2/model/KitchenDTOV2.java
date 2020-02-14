package com.algaworks.algafood.api.v2.model;

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
public class KitchenDTOV2 extends RepresentationModel<KitchenDTOV2> {
	
	@ApiModelProperty(example = "1")
	private Long idKitchen;
	
	@ApiModelProperty(example = "Cozinha americana")
	private String nameKitchen;

}
