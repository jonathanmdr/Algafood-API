package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Forma de Pagamento", description = "Representação de forma de pagamento")
@Getter
@Setter
public class PaymentFormIdInput {
	
	@ApiModelProperty(example = "1", required = true, position = 10)
	@NotNull
	private Long id;

}
