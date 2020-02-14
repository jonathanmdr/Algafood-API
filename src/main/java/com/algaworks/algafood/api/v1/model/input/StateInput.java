package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Estado", description = "Representação de um estado")
@Getter
@Setter
public class StateInput {
	
	@ApiModelProperty(example = "São Paulo", required = true)
	@NotBlank
	private String name;

}
