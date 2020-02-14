package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representação de cidade resumida")
@Getter
@Setter
public class CityIdInput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

}
