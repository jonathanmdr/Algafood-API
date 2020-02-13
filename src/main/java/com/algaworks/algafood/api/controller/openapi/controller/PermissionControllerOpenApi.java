package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.PermissionDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Permissões")
public interface PermissionControllerOpenApi {

	@ApiOperation("Lista todas as permissões")
	public CollectionModel<PermissionDTO> findAll();

	@ApiOperation("Busca uma permissão por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "Identificador da permissão inválido", response = ApiError.class),
			@ApiResponse(code = 404, message = "Permissão não encontrada", response = ApiError.class)
	})
	public PermissionDTO findById(@ApiParam(value = "Identificador de uma permissão", example = "1", required = true) Long permissionId);

}
