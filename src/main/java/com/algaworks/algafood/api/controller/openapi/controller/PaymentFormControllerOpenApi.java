package com.algaworks.algafood.api.controller.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.PaymentFormDTO;
import com.algaworks.algafood.api.model.input.PaymentFormInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface PaymentFormControllerOpenApi {
	
	@ApiOperation("Lista todas as formas de pagamento")
	public ResponseEntity<List<PaymentFormDTO>> findAll(ServletWebRequest request);
	
	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Identificador da forma de pagamento inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = ApiError.class)
	})
	public ResponseEntity<PaymentFormDTO> findById(@ApiParam(value = "Identificador de uma forma de pagamento", example = "1") Long formaPagamentoId, ServletWebRequest request);
	
	@ApiOperation("Cadastra uma nova forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
	})
	public PaymentFormDTO save(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento") PaymentFormInput paymentFormInput);
	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = ApiError.class)
	})
	public PaymentFormDTO update(@ApiParam(value = "Identificador de uma forma de pagamento", example = "1") Long paymentoFormId,			
			                     @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados") PaymentFormInput paymentFormInput);
	
	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = ApiError.class)
	})
	public void delete(@ApiParam(value = "Identificador de uma forma de pagamento", example = "1") Long formaPagamentoId);

}
