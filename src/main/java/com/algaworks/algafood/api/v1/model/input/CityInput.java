package com.algaworks.algafood.api.v1.model.input;

import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "Cidade", description = "Representação de cidade")
@Getter
@Setter
public class CityInput {
	
	@ApiModelProperty(example = "São Paulo", required = true)
	@NotBlank
	private String name;
	
	@Valid
	@NotNull
	private StateIdInput state;

}
