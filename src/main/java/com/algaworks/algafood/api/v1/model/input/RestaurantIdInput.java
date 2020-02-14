package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Restaurante", description = "Representação de Restaurante")
@Getter
@Setter
public class RestaurantIdInput {
	
	@ApiModelProperty(example = "1", required = true, position = 10)
	@NotNull
	private Long id;

}
