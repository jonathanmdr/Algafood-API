package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@ApiModel(value = "RestauranteResumoDTO", description = "Representação de restaurante de forma resumida")
@Getter
@Setter
public class RestaurantSummaryDTO extends RepresentationModel<RestaurantSummaryDTO> {

	@ApiModelProperty(example = "1", position = 10)
	private Long id;

	@ApiModelProperty(example = "Thai Delivery", position = 20)
	private String name;

	@ApiModelProperty(example = "8.00", position = 30)
	private BigDecimal freightRate;

	@ApiModelProperty(position = 40)
	private KitchenDTO kitchen;

}
