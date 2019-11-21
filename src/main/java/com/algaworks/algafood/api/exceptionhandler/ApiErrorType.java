package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {
	
	RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
	BUSINESS_EXCEPTION("/erro-de-negocio", "Violação de regra de negócio"),
	REQUEST_BODY_ERROR("/erro-no-corpo-da-requisicao", "Erro no corpo da requisição"),
	REQUEST_INVALID_PARAMETER("/parametro-invalido", "Parâmetro inválido"),
	INTERNAL_SERVER_ERROR("/erro-de-sistema", "Erro de sistema");
	
	private String title;
	private String uri;
	
	private ApiErrorType(String path, String title) {
		this.uri = "https://algafood.com.br".concat(path);
		this.title = title;
	}

}
