package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cozinha", description = "Representação de cozinha")
@Getter
@Setter
public class KitchenDTO {
	
	@ApiModelProperty(example = "1")
	@JsonView(RestaurantView.Summary.class)
	private Long id;
	
	@ApiModelProperty(example = "Cozinha americana")
	@JsonView(RestaurantView.Summary.class)
	private String name;

}
