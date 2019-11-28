package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProductNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<Product> findbyRestaurant(Restaurant restaurant) {
		return productRepository.findByRestaurant(restaurant);
	}
	
	@Transactional(readOnly = true)
	public Product findById(Long restaurantId, Long productId) {
		return productRepository.findById(restaurantId, productId)
				.orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
	}
	
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}

}
