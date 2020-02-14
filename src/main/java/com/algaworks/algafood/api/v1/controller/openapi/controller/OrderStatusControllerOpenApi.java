package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface OrderStatusControllerOpenApi {
	
	@ApiOperation("Confirmação de pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> confirm(@ApiParam(value = "Identificador do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String code);
	
	@ApiOperation("Confirmação de pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> deliver(@ApiParam(value = "Identificador do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String code);
	
	@ApiOperation("Confirmação de pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> cancel(@ApiParam(value = "Identificador do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String code);

}
