package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.openapi.controller.RestaurantProductControllerOpenApi;
import com.algaworks.algafood.api.mapper.ProductMapper;
import com.algaworks.algafood.api.model.ProductDTO;
import com.algaworks.algafood.api.model.input.ProductInput;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController implements RestaurantProductControllerOpenApi {

	@Autowired
	private ProductService productService;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<ProductDTO> findByRestaurant(@PathVariable Long restaurantId, @RequestParam(required = false, defaultValue = "false") Boolean includingInactives) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		List<Product> productsDto;

		if (includingInactives) {
			productsDto = productService.findAllByRestaurant(restaurant);
		} else {
			productsDto = productService.findActiveByRestaurant(restaurant);
		}

		return productMapper.toCollectionModel(productsDto)
				.removeLinks()
				.add(algaLinks.linkToProdutcs(restaurantId));
	}

	@Override
	@GetMapping("/{productId}")
	public ProductDTO findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
		return productMapper.toModel(productService.findById(restaurantId, productId));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO save(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		Product product = productMapper.toDomainObject(productInput);
		product.setRestaurant(restaurant);

		return productMapper.toModel(productService.save(product));
	}

	@Override
	@PutMapping("/{productId}")
	public ProductDTO update(@PathVariable Long restaurantId, @PathVariable Long productId,	@RequestBody @Valid ProductInput productInput) {
		Product productCurrent = productService.findById(restaurantId, productId);

		productMapper.copyToDomainObject(productInput, productCurrent);

		return productMapper.toModel(productService.save(productCurrent));
	}

}
