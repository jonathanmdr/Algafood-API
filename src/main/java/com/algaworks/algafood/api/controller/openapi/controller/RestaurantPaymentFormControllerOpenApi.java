package com.algaworks.algafood.api.controller.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.PaymentFormDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestaurantPaymentFormControllerOpenApi {
	
	@ApiOperation("Lista todas as formas de pagamento por restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public List<PaymentFormDTO> findAll(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Associação de restaurante com forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = ApiError.class)
	})
	public void associatePaymentForm(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId, 
			                         @ApiParam(value = "Identificador de uma forma de pagamento", example = "1", required = true) Long paymentFormId);
	
	@ApiOperation("Desassociação de restaurante com forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = ApiError.class)
	})
	public void disassociatePaymentForm(@ApiParam(value = "Identificador de um restaurante", example = "1", required = true) Long restaurantId, 
			                            @ApiParam(value = "Identificador de uma forma de pagamento", example = "1", required = true) Long paymentFormId);

}
