package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.ProductDTO;
import com.algaworks.algafood.api.model.input.ProductInput;
import com.algaworks.algafood.domain.model.Product;

@Component
public class ProductMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProductDTO toDto(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}
	
	public List<ProductDTO> toCollectionDto(List<Product> products) {
		return products.stream().map(product -> toDto(product)).collect(Collectors.toList());
	}
	
	public Product toDomainObject(ProductInput productInput) {
		return modelMapper.map(productInput, Product.class);
	}
	
	public void copyToDomainObject(ProductInput productInput, Product product) {
		modelMapper.map(productInput, product);
	}

}
