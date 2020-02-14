package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.ProductDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ProdutosModel")
@Getter
@Setter
public class ProductsModelOpenApi {

	private ProductEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("ProdutosEmbeddedModel")
	@Getter
	@Setter
	private class ProductEmbeddedModelOpenApi {

		private List<ProductDTO> product;

	}

}
