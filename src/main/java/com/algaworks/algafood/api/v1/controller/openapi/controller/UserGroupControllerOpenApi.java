package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.GroupDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UserGroupControllerOpenApi {
	
	@ApiOperation("Lista todos os grupos por usuário")
	public CollectionModel<GroupDTO> findAllByUserId(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId);
	
	@ApiOperation("Associação de usuário com grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> associateGroup(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId, 
			                   @ApiParam(value = "Identificador do grupo", example = "1", required = true) Long groupId);
	
	@ApiOperation("Desassociação de usuário com grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> disassociateGroup(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId, 
                                  @ApiParam(value = "Identificador do grupo", example = "1", required = true) Long groupId);

}
