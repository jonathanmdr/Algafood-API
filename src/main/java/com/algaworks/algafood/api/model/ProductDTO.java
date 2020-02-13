package com.algaworks.algafood.api.model;

import lombok.Setter;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Relation(collectionRelation = "products")
@ApiModel(value = "Produto", description = "Representação de produto")
@Getter
@Setter
public class ProductDTO extends RepresentationModel<ProductDTO> {

	@ApiModelProperty(example = "1", position = 10)
	private Long id;

	@ApiModelProperty(example = "1", position = 10)
	private String name;

	@ApiModelProperty(example = "1", position = 10)
	private String description;

	@ApiModelProperty(example = "1", position = 10)
	private BigDecimal price;

	@ApiModelProperty(example = "1", position = 10)
	private Boolean active;

}
