package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordInput {
	
	@NotBlank
	private String currentPassword;
	
	@NotBlank
	private String newPassword;	

}
