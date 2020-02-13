package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.PermissionDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GroupPermissionControllerOpenApi {
	
	@ApiOperation("Lista todas as permissões por grupo")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiError.class)
	})
	public CollectionModel<PermissionDTO> findById(@ApiParam(value = "Identificador de um grupo", example = "1", required = true) Long groupId);
	
	@ApiOperation("Associação de grupo com permissão")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", response = ApiError.class)
	})
	public ResponseEntity<Void> associatePermission(@ApiParam(value = "Identificador de um grupo", example = "1", required = true) Long groupId, 
			                        @ApiParam(value = "Identificador de uma permissão", example = "1", required = true) Long permissionId);
	
	@ApiOperation("Desassociação de grupo com permissão")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", response = ApiError.class)
	})
	public ResponseEntity<Void> disassociatePermission(@ApiParam(value = "Identificador de um grupo", example = "1", required = true) Long groupId, 
                                       @ApiParam(value = "Identificador de uma permissão", example = "1", required = true) Long permissionId);

}
