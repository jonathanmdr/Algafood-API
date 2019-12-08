package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.ProductMapper;
import com.algaworks.algafood.api.model.ProductDTO;
import com.algaworks.algafood.api.model.input.ProductInput;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private ProductMapper productMapper;
	
	@GetMapping
	public List<ProductDTO> findByRestaurant(@PathVariable Long restaurantId, @RequestParam(required = false) boolean includingInactives) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		
		if (includingInactives) {
			return productMapper.toCollectionDto(productService.findAllByRestaurant(restaurant));
		}
		
		return productMapper.toCollectionDto(productService.findActiveByRestaurant(restaurant));		
	}
	
	@GetMapping("/{productId}")
	public ProductDTO findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
		return productMapper.toDto(productService.findById(restaurantId, productId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO save(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		
		Product product = productMapper.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		
		return productMapper.toDto(productService.save(product));
	}
	
	@PutMapping("/{productId}")
	public ProductDTO update(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput) {
		Product productCurrent = productService.findById(restaurantId, productId);
		
		productMapper.copyToDomainObject(productInput, productCurrent);
		
		return productMapper.toDto(productService.save(productCurrent));
	}

}
