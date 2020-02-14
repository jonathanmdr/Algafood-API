package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CozinhaInput", description = "Representação de cozinha")
@Getter
@Setter
public class KitchenInput {
	
	@ApiModelProperty(example = "Cozinha americana", required = true)
	@NotBlank
	private String name;

}
