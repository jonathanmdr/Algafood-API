package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepository;

@Service
public class CatalogProductPhotoService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional
	public ProductPhoto save(ProductPhoto photo) {
		return productRepository.save(photo);
	}

}
