package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@ApiModel(value = "Restaurante somente nome", description = "Representação de restaurante apresentando somente nome e ID")
@Getter
@Setter
public class RestaurantJustNameDTO extends RepresentationModel<RestaurantJustNameDTO> {

	@ApiModelProperty(example = "1", position = 10)
	private Long id;

	@ApiModelProperty(example = "Thai Delivery", position = 20)
	private String name;

}
