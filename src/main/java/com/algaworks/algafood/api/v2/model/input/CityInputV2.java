package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CidadeInput", description = "Representação de cidade")
@Getter
@Setter
public class CityInputV2 {
	
	@ApiModelProperty(example = "São Paulo", required = true)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long idState;

}
