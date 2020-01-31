package com.algaworks.algafood.api.controller.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.KitchenDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Restaurante", description = "Representação de restaurante")
@Getter
@Setter
public class RestaurantModelOpenApi {
	
	@ApiModelProperty(example = "1", position = 10)
	private Long id;
	
	@ApiModelProperty(example = "Thai Delivery", position = 20)
	private String name;
	
	@ApiModelProperty(example = "8.00", position = 40)
	private BigDecimal freightRate;
	
	@ApiModelProperty(position = 50)
	private KitchenDTO kitchen;

}
