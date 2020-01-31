package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representação de cidade")
@Getter
@Setter
public class CitySummaryDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Blumenau")
	private String name;
	
	@ApiModelProperty(example = "Santa Catarina")
	private String state;

}
