package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "FormaDePagamentoInput", description = "Representação de forma de pagamento")
@Getter
@Setter
public class PaymentFormInput {
	
	@ApiModelProperty(example = "Cartão de crédito", required = true)
	@NotBlank
	private String name;

}
