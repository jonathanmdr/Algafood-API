package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "EnderecoDTO", description = "Representação de endereço")
@Getter
@Setter
public class AddressDTO {
	
	@ApiModelProperty(example = "89030110", position = 10)
	private String zipCode;
	
	@ApiModelProperty(example = "Rua das palmeiras", position = 20)
	private String publicPlace;
	
	@ApiModelProperty(example = "1234", position = 30)
	private String number;
	
	@ApiModelProperty(example = "Apartamento 916", position = 40)
	private String complement;
	
	@ApiModelProperty(example = "Centro", position = 50)
	private String neighborhood;
	
	@ApiModelProperty(position = 60)
	private CitySummaryDTO city;

}
