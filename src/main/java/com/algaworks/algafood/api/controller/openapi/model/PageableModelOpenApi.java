package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Pageable")
public class PageableModelOpenApi {

	@ApiModelProperty(example = "0", value = "Número da página (inicia com 0)")
	private int page;

	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;

	@ApiModelProperty(example = "name,asc", value = "Nome da propriedade para ordenação e tipo de ordenação (asc ou desc)")
	private List<String> sort;

}
