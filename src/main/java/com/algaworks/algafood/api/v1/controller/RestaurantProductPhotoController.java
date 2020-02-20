package com.algaworks.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.v1.controller.openapi.controller.RestaurantProductPhotoControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.ProductPhotoInput;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.api.v1.mapper.ProductPhotoMapper;
import com.algaworks.algafood.api.v1.model.ProductPhotoDTO;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.service.CatalogProductPhotoService;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.algaworks.algafood.domain.service.PhotoStorageService.PhotoRecovered;
import com.algaworks.algafood.domain.service.ProductService;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products/{productId}/photo", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {
	
	@Autowired
	private CatalogProductPhotoService catalogProductService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PhotoStorageService photoStorageService;
	
	@Autowired
	private ProductPhotoMapper productPhotoMapper;
	
	@Override
	@Security.Restaurants.AllowedConsult
	@GetMapping
	public ProductPhotoDTO findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
		ProductPhoto productPhoto = catalogProductService.findById(restaurantId, productId);
		return productPhotoMapper.toModel(productPhoto);
	}
	
	@Override
	@Security.Restaurants.AllowedConsult
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			ProductPhoto productPhoto = catalogProductService.findById(restaurantId, productId);
			
			MediaType mediaTypePhoto = MediaType.parseMediaType(productPhoto.getContentType());
			List<MediaType> mediaTypesAcceptable = MediaType.parseMediaTypes(acceptHeader);
			verifyCompabilityMediaType(mediaTypePhoto, mediaTypesAcceptable);
			
			PhotoRecovered photoRecovered = photoStorageService.recovery(productPhoto.getFileName());
			
			if (photoRecovered.hasUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, photoRecovered.getUrl())
						.build();
			} else {
				return ResponseEntity.ok()
						.contentType(mediaTypePhoto)
						.body(new InputStreamResource(photoRecovered.getInputStream()));
			}
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@Security.Restaurants.AllowedUserManager
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoDTO updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput, @RequestPart(required = true) MultipartFile file) throws IOException {
		Product product = productService.findById(restaurantId, productId);
		
		ProductPhoto photo = new ProductPhoto();
		photo.setProduto(product);
		photo.setDescription(productPhotoInput.getDescription());
		photo.setContentType(productPhotoInput.getFile().getContentType());
		photo.setSize(productPhotoInput.getFile().getSize());
		photo.setFileName(productPhotoInput.getFile().getOriginalFilename());
		
		return productPhotoMapper.toModel(catalogProductService.save(photo, productPhotoInput.getFile().getInputStream()));
	}
	
	@Override
	@Security.Restaurants.AllowedUserManager
	@DeleteMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restaurantId, @PathVariable Long productId) {
		catalogProductService.delete(restaurantId, productId);
	}
	
	private void verifyCompabilityMediaType(MediaType mediaType, List<MediaType> mediaTypesAcceptable) throws HttpMediaTypeNotAcceptableException {
		boolean accept = mediaTypesAcceptable.stream()
				.anyMatch(mediaTypeAcceptable -> mediaTypeAcceptable.isCompatibleWith(mediaType));
		
		if (!accept) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAcceptable);
		}
	}

}
