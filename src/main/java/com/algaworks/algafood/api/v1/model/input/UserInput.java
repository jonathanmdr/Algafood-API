package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "UsuarioInput", description = "Representação de usuário")
@Getter
@Setter
public class UserInput {
	
	@ApiModelProperty(example = "Jonathan Henrique", required = true)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "jonathan.henrique@algafood.com.br", required = true)
	@NotBlank
	@Email
	private String email;
	
	@ApiModelProperty(example = "a9d9p8.E10", required = true)
	@NotBlank
	private String password;

}
