package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.StateDTO;
import com.algaworks.algafood.api.model.input.StateInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface StateControllerOpenApi {

	@ApiOperation("Lista todos os estados")
	public CollectionModel<StateDTO> findAll();
	
	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de estado inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class)
	})
	public StateDTO findById(@ApiParam(value = "Identificador de um estado", example = "1", required = true) Long stateId);
	
	@ApiOperation("Cadastra um novo estado")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado cadastrado")
	})	
	public StateDTO save(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) StateInput stateInput);
	
	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Estado atualizada"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class)
	})
	public StateDTO update(@ApiParam(value = "Identificador de um estado", example = "1", required = true) Long stateId, 
			               @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) StateInput stateInput);
	
	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Estado excluído"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class),
		@ApiResponse(code = 409, message = "Estado não pode ser excluído pois está associado a outro recurso", response = ApiError.class)
	})
	public void delete(@ApiParam(value = "Identificador de um estado", example = "1", required = true) Long stateId);
	
}
