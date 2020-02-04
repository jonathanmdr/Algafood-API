package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cities")
@ApiModel(value = "Cidade", description = "Representação de cidade")
@Getter
@Setter
public class CityDTO extends RepresentationModel<CityDTO> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "São Paulo")
	private String name;

	private StateDTO state;

}
