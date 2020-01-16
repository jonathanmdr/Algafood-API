package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Estado", description = "Representação de UF")
@Getter
@Setter
public class StateIdInput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

}
