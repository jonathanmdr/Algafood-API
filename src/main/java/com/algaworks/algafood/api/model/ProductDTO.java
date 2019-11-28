package com.algaworks.algafood.api.model;

import lombok.Setter;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
@Setter
public class ProductDTO {
	
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Boolean active;

}
