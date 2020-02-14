package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "users")
@ApiModel(value = "Usuário Resumo", description = "Representação de usuário resumida")
@Getter
@Setter
public class UserSummaryDTO extends RepresentationModel<UserSummaryDTO> {

	@ApiModelProperty(example = "1", position = 10)
	private Long id;

	@ApiModelProperty(example = "Jonathan Henrique", position = 20)
	private String name;

	@ApiModelProperty(example = "jonathan.henrique@algafood.com.br", position = 30)
	private String email;

}
