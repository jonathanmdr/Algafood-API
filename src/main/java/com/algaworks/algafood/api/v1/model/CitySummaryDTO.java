package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cities")
@ApiModel(value = "CidadeResumoDTO", description = "Representação de cidade")
@Getter
@Setter
public class CitySummaryDTO extends RepresentationModel<CitySummaryDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Blumenau")
	private String name;

	@ApiModelProperty(example = "Santa Catarina")
	private String state;

}
