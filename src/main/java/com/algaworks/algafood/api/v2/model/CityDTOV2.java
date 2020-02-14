package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cities")
@ApiModel(value = "CidadeDTO", description = "Representação de cidade")
@Getter
@Setter
public class CityDTOV2 extends RepresentationModel<CityDTOV2> {

	@ApiModelProperty(example = "1", position = 10)
	private Long idCity;

	@ApiModelProperty(example = "São Paulo", position = 20)
	private String nameCity;

	@ApiModelProperty(example = "1", position = 30)
	private Long idState;

	@ApiModelProperty(example = "São Paulo", position = 40)
	private String nameState;

}
