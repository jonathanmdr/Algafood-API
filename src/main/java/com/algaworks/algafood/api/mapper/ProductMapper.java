package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestaurantProductController;
import com.algaworks.algafood.api.model.ProductDTO;
import com.algaworks.algafood.api.model.input.ProductInput;
import com.algaworks.algafood.domain.model.Product;

@Component
public class ProductMapper extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public ProductMapper() {
		super(RestaurantProductController.class, ProductDTO.class);
	}

	@Override
	public ProductDTO toModel(Product product) {
		ProductDTO productDto = createModelWithId(product.getId(), product, product.getRestaurant().getId());
		modelMapper.map(product, productDto);

		productDto.add(algaLinks.linkToProdutcs(product.getRestaurant().getId(), "products"));
		productDto.add(algaLinks.linkToProductPhoto(product.getRestaurant().getId(), product.getId(), "photo"));

		return productDto;
	}	

	public Product toDomainObject(ProductInput productInput) {
		return modelMapper.map(productInput, Product.class);
	}

	public void copyToDomainObject(ProductInput productInput, Product product) {
		modelMapper.map(productInput, product);
	}

}
