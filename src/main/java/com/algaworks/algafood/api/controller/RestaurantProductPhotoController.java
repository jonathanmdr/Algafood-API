package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.ProductMapper;
import com.algaworks.algafood.api.model.ProductPhotoDTO;
import com.algaworks.algafood.api.model.input.ProductPhotoInput;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.service.CatalogProductPhotoService;
import com.algaworks.algafood.domain.service.ProductService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
	
	@Autowired
	private CatalogProductPhotoService catalogProductService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductMapper productMapper;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoDTO updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) {
		Product product = productService.findById(restaurantId, productId);
		
		ProductPhoto photo = new ProductPhoto();
		photo.setProduct(product);
		photo.setDescription(productPhotoInput.getDescription());
		photo.setContentType(productPhotoInput.getFile().getContentType());
		photo.setSize(productPhotoInput.getFile().getSize());
		photo.setFileName(productPhotoInput.getFile().getOriginalFilename());
		
		return productMapper.toDto(catalogProductService.save(photo));
	}

}
