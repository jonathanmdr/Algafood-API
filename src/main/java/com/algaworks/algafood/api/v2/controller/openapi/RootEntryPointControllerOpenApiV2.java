package com.algaworks.algafood.api.v2.controller.openapi;

import com.algaworks.algafood.api.v2.controller.RootEntryPointControllerV2.RootEntryPointDTOV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Root")
public interface RootEntryPointControllerOpenApiV2 {

	@ApiOperation("Lista todas as URL's de entrada da API")		
	public RootEntryPointDTOV2 root();
		
}
