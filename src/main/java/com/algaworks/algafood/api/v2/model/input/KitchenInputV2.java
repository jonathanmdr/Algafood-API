package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CozinhaInput", description = "Representação de cozinha")
@Getter
@Setter
public class KitchenInputV2 {
	
	@ApiModelProperty(example = "Cozinha americana", required = true)
	@NotBlank
	private String nameKitchen;

}
