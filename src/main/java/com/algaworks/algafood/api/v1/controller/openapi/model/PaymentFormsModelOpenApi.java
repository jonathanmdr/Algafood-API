package com.algaworks.algafood.api.v1.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.PaymentFormDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("FormasPagamentoModel")
@Getter
@Setter
public class PaymentFormsModelOpenApi {
	
	private PaymentFormEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("FormasPagamentoEmbeddedModel")
	@Getter
	@Setter
	private class PaymentFormEmbeddedModelOpenApi {
		
		private List<PaymentFormDTO> paymentForms;
		
	}

}
