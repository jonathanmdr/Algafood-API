package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.input.ProductPhotoInput;
import com.algaworks.algafood.api.v1.model.ProductPhotoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestaurantProductPhotoControllerOpenApi {
	
	@ApiOperation(value = "Busca a foto do produto de um restaurante", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador do restaurante ou produto inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Foto de produto não encontrada", response = ApiError.class)
	})
	public ProductPhotoDTO findById(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
                                    @ApiParam(value = "Identificador do produto", example = "1", required = true) Long productId);
	
	@ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
	public ResponseEntity<?> servePhoto(Long restaurantId, Long productId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	@ApiOperation("Atualiza a foto do produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Foto do produto atualizada"),
		@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiError.class)
	})
	public ProductPhotoDTO updatePhoto(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
			                           @ApiParam(value = "Identificador do produto", example = "1", required = true) Long productId, 
			                           ProductPhotoInput productPhotoInput,
			                           @ApiParam(value = "Arquivo da foto do produto (Máximo 500KB, apenas JPEG e PNG)", required = true) MultipartFile file) throws IOException;
    
	@ApiOperation("Exclui a foto do produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Foto do produto excluída"),
		@ApiResponse(code = 400, message = "Identificador do restaurante ou produto inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Foto de produto não encontrada", response = ApiError.class)
	})
	public void delete(@ApiParam(value = "Identificador do restaurante", example = "1", required = true) Long restaurantId, 
                       @ApiParam(value = "Identificador do produto", example = "1", required = true) Long productId);

}
