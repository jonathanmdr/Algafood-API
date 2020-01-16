package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Grupo", description = "Representação de grupo de usuário")
@Getter
@Setter
public class GroupDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Administrador")
	private String name;

}
