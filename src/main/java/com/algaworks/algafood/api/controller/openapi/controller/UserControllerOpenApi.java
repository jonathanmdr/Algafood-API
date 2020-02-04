package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.UserSummaryDTO;
import com.algaworks.algafood.api.model.input.UserInput;
import com.algaworks.algafood.api.model.input.UserPasswordInput;
import com.algaworks.algafood.api.model.input.UserSummaryInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UserControllerOpenApi {
	
	@ApiOperation("Lista todos os usuários")
	public CollectionModel<UserSummaryDTO> findAll();
	
	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador do usuário inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class)
	})
	public UserSummaryDTO findById(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário cadastrado")
	})
	public UserSummaryDTO save(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UserInput userInput);
	
	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário atualizado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class)
	})
	public UserSummaryDTO update(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId, 
			                     @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UserSummaryInput userSummaryInput);
	
	@ApiOperation("Atualiza a senha de um usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Senha alterada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class)
	})
	public void updatePassword(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId, 
			                   @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) UserPasswordInput userPasswordInput);
	
	
	@ApiOperation("Exclui um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuáruio excluído com sucesso"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class),
		@ApiResponse(code = 409, message = "Usuário não pode ser excluído pois está em uso por outro recurso", response = ApiError.class)
	})
	public void delete(@ApiParam(value = "Identificador do usuário", example = "1", required = true) Long userId);

}
