package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Restaurante", description = "Representação de restaurante")
@Getter
@Setter
public class RestaurantInput {
	
	@ApiModelProperty(example = "Thai Delivery", required = true, position = 10)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "12.50", required = true, position = 20)
	@NotNull
	@PositiveOrZero
	private BigDecimal freightRate;
	
	@ApiModelProperty(required = true, position = 30)
	@Valid
	@NotNull
	private KitchenIdInput kitchen;
	
	@ApiModelProperty(required = true, position = 40)
	@Valid
	@NotNull
	private AddressInput address;

}
