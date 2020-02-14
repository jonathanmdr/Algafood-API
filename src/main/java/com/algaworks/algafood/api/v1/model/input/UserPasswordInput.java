package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "UsuarioSenhaInput", description = "Representação de atualização de senha de usuário")
@Getter
@Setter
public class UserPasswordInput {
	
	@ApiModelProperty(example = "a9d9p8.E10", required = true)
	@NotBlank
	private String currentPassword;
	
	@ApiModelProperty(example = "@23xPtO.3$", required = true)
	@NotBlank
	private String newPassword;	

}
