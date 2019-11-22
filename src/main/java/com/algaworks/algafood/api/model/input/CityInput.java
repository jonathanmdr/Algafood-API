package com.algaworks.algafood.api.model.input;

import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
@Setter
public class CityInput {
	
	@NotBlank
	private String name;
	
	@Valid
	@NotNull
	private StateIdInput state;

}
