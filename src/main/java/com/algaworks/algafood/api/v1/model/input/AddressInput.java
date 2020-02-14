package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Endereço", description = "Representação de endereço")
@Getter
@Setter
public class AddressInput {

	@ApiModelProperty(example = "89030110", required = true, position = 10)
	@NotBlank
	private String zipCode;
	
	@ApiModelProperty(example = "Rua das palmeiras", required = true, position = 20)
	@NotBlank
	private String publicPlace;
	
	@ApiModelProperty(example = "1234", required = true, position = 30)
	@NotBlank
	private String number;
	
	@ApiModelProperty(example = "Apartamento 916", required = true, position = 40)
	private String complement;
	
	@ApiModelProperty(example = "Centro", required = true, position = 50)
	@NotBlank
	private String neighborhood;	
	
	@ApiModelProperty(required = true, position = 60)
	@Valid
	@NotNull
	private CityIdInput city;
	
}
