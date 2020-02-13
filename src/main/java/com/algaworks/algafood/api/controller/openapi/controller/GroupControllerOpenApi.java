package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.GroupDTO;
import com.algaworks.algafood.api.model.input.GroupInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GroupControllerOpenApi {
	
	@ApiOperation("Lista todos os grupos")
	public CollectionModel<GroupDTO> findAll();
	
	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de grupo inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiError.class)
	})	
	public GroupDTO findById(@ApiParam(value = "Identificador de um grupo", example = "1") Long groupId);
	
	@ApiOperation("Cadastra um novo grupo")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Grupo cadastrado")
	})
	public GroupDTO save(@ApiParam(name = "corpo", value = "Representação de um novo grupo") GroupInput groupInput);
	
	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Grupo atualizado"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiError.class)
	})	
	public GroupDTO update(@ApiParam(value = "Identificador de um grupo", example = "1") Long groupId, 
						   @ApiParam(name = "corpo", value = "Representação de um grupo com novos dados") GroupInput groupInput);
	
	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo excluído"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiError.class)
	})
	public void delete(@ApiParam(value = "Identificador de um grupo", example = "1") Long groupId);

}
