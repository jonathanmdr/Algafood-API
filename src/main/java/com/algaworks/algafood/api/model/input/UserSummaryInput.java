package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummaryInput {
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Email
	private String email;

}
