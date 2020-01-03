package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.PhotoNotFoundException;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.PhotoStorageService.NewPhoto;

@Service
public class CatalogProductPhotoService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PhotoStorageService photoStorageService;
	
	@Transactional(readOnly = true)
	public ProductPhoto findById(Long restaurantId, Long productId) {
		return findOrFail(restaurantId, productId);
	}
	
	@Transactional
	public ProductPhoto save(ProductPhoto photo, InputStream dataFile) {
		Long restaurantId = photo.getRestaurantId();
		Long productId = photo.getProduto().getId();
		String newFileName = photoStorageService.generateFileName(photo.getFileName());
		String oldFileName = null;
		
		Optional<ProductPhoto> photoExistent = productRepository.findPhotoById(restaurantId, productId);
		
		if (photoExistent.isPresent()) {
			oldFileName = photoExistent.get().getFileName();
			productRepository.delete(photoExistent.get());
		}			
		
		photo.setFileName(newFileName);
		
		ProductPhoto photoSaved = productRepository.save(photo);
		productRepository.flush();
		
		NewPhoto newPhoto = NewPhoto.builder()
				.fileName(photoSaved.getFileName())
				.inputStream(dataFile)
				.build();
		
		photoStorageService.replace(oldFileName, newPhoto);
		
		return photoSaved;
	}
	
	@Transactional
	public void delete(Long restaurantId, Long productId) {
		ProductPhoto productPhoto = findOrFail(restaurantId, productId);
		
		productRepository.delete(productPhoto);
		productRepository.flush();
		
		photoStorageService.remove(productPhoto.getFileName());
	}
	
	private ProductPhoto findOrFail(Long restaurantId, Long productId) {
		return productRepository.findPhotoById(restaurantId, productId)
				.orElseThrow(() -> new PhotoNotFoundException(restaurantId, productId));
	}

}
