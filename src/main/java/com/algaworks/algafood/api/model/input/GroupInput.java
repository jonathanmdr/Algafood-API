package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Grupo", description = "Representação de grupo de usuário")
@Getter
@Setter
public class GroupInput {
	
	@ApiModelProperty(example = "Administrador", required = true)
	@NotBlank
	private String name;

}
