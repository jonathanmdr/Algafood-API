package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "UsuarioResumidoInput", description = "Representação de atualização de usuário")
@Getter
@Setter
public class UserSummaryInput {
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Email
	private String email;

}
