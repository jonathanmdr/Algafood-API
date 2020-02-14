package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.input.KitchenInput;
import com.algaworks.algafood.api.v1.representation.KitchensXmlWrapper;
import com.algaworks.algafood.api.v1.model.KitchenDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface KitchenControllerOpenApi {

	@ApiOperation("Lista todas as cozinhas")
	public PagedModel<KitchenDTO> findAll(Pageable pageable);

	@ApiOperation("Lista todas as cozinhas")
	public KitchensXmlWrapper findAllInFormatXml(Pageable pageable);

	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador da cozinha inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiError.class)
	})
	public KitchenDTO findById(@ApiParam(value = "Identificador de uma cozinha", example = "1") Long kitchenId);

	@ApiOperation("Cadastra uma nova cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha cadastrada")
	})
	public KitchenDTO save(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha") KitchenInput kitchenInput);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiError.class)
	})
	public KitchenDTO update(@ApiParam(value = "Identificador de uma cozinha", example = "1") Long kitchenId,
			                 @ApiParam(name = "corpo", value = "Representação de uma cozinha com novos dados") KitchenInput kitchenInput);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiError.class)
	})
	public void delete(@ApiParam(value = "Identificador de uma cozinha", example = "1") Long kitchenId);

}
