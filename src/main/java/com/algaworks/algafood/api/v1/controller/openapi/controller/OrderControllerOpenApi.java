package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.input.OrderInput;
import com.algaworks.algafood.api.v1.model.OrderDTO;
import com.algaworks.algafood.api.v1.model.OrderSummaryDTO;
import com.algaworks.algafood.domain.filter.OrderFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface OrderControllerOpenApi {
	
	@ApiOperation("Lista todos os pedidos de forma resumida")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome das propriedades para filtrar na resposta, separados por vírgula", name = "fields", paramType = "query", type = "string")
	})
	public PagedModel<OrderSummaryDTO> findAll(OrderFilter orderFilter, Pageable pageable);
	
	@ApiOperation("Busca um pedido por UUID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de pedido inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiError.class)
	})
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome das propriedades para filtrar na resposta, separados por vírgula", name = "fields", paramType = "query", type = "string")
	})
	public OrderDTO findById(@ApiParam(value = "Identificador de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String code);
	
	@ApiOperation("Salva um pedido")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Pedido cadastrado")
	})	
	public OrderDTO save(@ApiParam(name = "corpo", value = "Representação de um pedido com novos dados") OrderInput orderInput);

}
