package com.algaworks.algafood.api.v1.controller.openapi.controller;

import com.algaworks.algafood.api.v1.controller.RootEntryPointController.RootEntryPointDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Root")
public interface RootEntryPointControllerOpenApi {

	@ApiOperation("Lista todas as URL's de entrada da API")		
	public RootEntryPointDTO root();
		
}
