package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestaurantProductPhotoController;
import com.algaworks.algafood.api.v1.model.ProductPhotoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.ProductPhoto;

@Component
public class ProductPhotoMapper extends RepresentationModelAssemblerSupport<ProductPhoto, ProductPhotoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public ProductPhotoMapper() {
		super(RestaurantProductPhotoController.class, ProductPhotoDTO.class);
	}

	@Override
	public ProductPhotoDTO toModel(ProductPhoto productPhoto) {
		ProductPhotoDTO productPhotoDto = modelMapper.map(productPhoto, ProductPhotoDTO.class);

		/**
		 * Usuário com permissão de consultar restaurantes também pode consultar os produtos do mesmo.
		 */
		if (algaSecurity.canConsultingRestaurants()) {
		    productPhotoDto.add(algaLinks.linkToProductPhoto(productPhoto.getRestaurantId(), productPhoto.getId()));
		    productPhotoDto.add(algaLinks.linkToProduct(productPhoto.getRestaurantId(), productPhoto.getId(), "product"));
		}

		return productPhotoDto;
	}

}
