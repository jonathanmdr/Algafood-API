package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.ProductDTO;
import com.algaworks.algafood.api.model.input.ProductInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestaurantProductControllerOpenApi {
	
	@ApiOperation("Lista os produtos de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador do restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public CollectionModel<ProductDTO> findByRestaurant(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
			                                 @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", example = "false", defaultValue = "false") Boolean includingInactives);
	
	@ApiOperation("Busca um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador do restaurante ou produto inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiError.class)
	})
	public ProductDTO findById(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
			                   @ApiParam(value = "Identificador do produto", example = "1", required = true) Long productId);
	
	@ApiOperation("Cadastra um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Produto cadastrado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public ProductDTO save(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
			               @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProductInput productInput);
	
	@ApiOperation("Atualiza um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Produto atualizado"),
		@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiError.class)
	})
	public ProductDTO update(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
                             @ApiParam(value = "Identificador do produto", example = "1", required = true) Long productId,
                             @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", required = true) ProductInput productInput);

}
