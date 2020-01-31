package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Restaurante Resumido", description = "Representação de restaurante de forma resumida")
@Getter
@Setter
public class RestaurantSummaryDTO {
	
	@ApiModelProperty(example = "1", position = 10)
	private Long id;
	
	@ApiModelProperty(example = "Thai Delivery", position = 10)
	private String name;

}
