package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.CityDTO;
import com.algaworks.algafood.api.model.input.CityInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CityControllerOpenApi {

	@ApiOperation("Lista todas as cidades")		
	public CollectionModel<CityDTO> findAll();
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador da cidade inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiError.class)
	})	
	public CityDTO findById(@ApiParam(value = "Identificador de uma cidade", example = "1", required = true) Long cityId);
	
	@ApiOperation("Cadastra uma nova cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})		
	public CityDTO save(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CityInput cityInput);
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiError.class)
	})	
	public CityDTO update(@ApiParam(value = "Identificador de uma cidade", example = "1", required = true) Long cityId, 
			              @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados", required = true) CityInput cityInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiError.class),
		@ApiResponse(code = 409, message = "Cidade não pode ser excluída pois está associada a outro recurso", response = ApiError.class)
	})	
	public void delete(@ApiParam(value = "Identificador de uma cidade", example = "1", required = true) Long cityId);
		
}
