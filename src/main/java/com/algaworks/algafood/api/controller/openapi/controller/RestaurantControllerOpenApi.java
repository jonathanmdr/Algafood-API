package com.algaworks.algafood.api.controller.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.controller.openapi.model.RestaurantModelOpenApi;
import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.api.model.RestaurantJustNameDTO;
import com.algaworks.algafood.api.model.RestaurantSummaryDTO;
import com.algaworks.algafood.api.model.input.RestaurantInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestaurantControllerOpenApi {

	@ApiOperation(value = "Lista todos os restaurantes de forma resumida", response = RestaurantModelOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "just-name", name = "projection", paramType = "query", type = "string")
	})
	public CollectionModel<RestaurantSummaryDTO> findAllSummary();
	
	@ApiOperation(value = "Lista todos os restaurantes apresentando somente o nome", hidden = true)
	public CollectionModel<RestaurantJustNameDTO> findAllJustName();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})	
	public RestaurantDTO findById(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Cadastra um novo restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante cadastrado")
	})
	public RestaurantDTO save(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestaurantInput restaurantInput);
	
	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public RestaurantDTO update(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId, 
			                    @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestaurantInput restaurantInput);
	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> activate(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Ativa restaurantes em lote por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public void activations(@ApiParam(name = "corpo", value = "Lista de identificadores de restaurantes", required = true) List<Long> restaurantIds);
	
	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> open(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> close(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Exclui um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante excluído"),
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class),
		@ApiResponse(code = 409, message = "Restaurante não pode ser excluído pois está associado a outro recurso", response = ApiError.class)
	})	
	public void delete(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public ResponseEntity<Void> inactivate(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Inativa restaurantes em lote por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public void inactivations(@ApiParam(name = "corpo", value = "Lista de identificadores de restaurantes", required = true) List<Long> restaurantIds);
	
	@ApiOperation("Atualiza um restaurante por ID parcialmente")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador de restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public RestaurantDTO updatePartial(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId, 
			                           @ApiParam(name = "corpo", value = "Representação dos campos que deseja alterar", required = true) Map<String, Object> values, HttpServletRequest request);
	
}
