package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.UserSummaryDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestaurantUserManagerControllerOpenApi {
	
	@ApiOperation("Lista os usuários responsáveis associados ao restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public CollectionModel<UserSummaryDTO> findAllByRestaurantId(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Associação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = ApiError.class)
	})
	public void associateUser(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId, 
			                  @ApiParam(value = "Identificador de um usuário", example = "1", required = true) Long userId);
	
	@ApiOperation("Desassociação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = ApiError.class)
	})
	public void disassociateUser(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId,
			                     @ApiParam(value = "Identificador de um usuário", example = "1", required = true) Long userId);

}
