package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Filtro de Pedidos", description = "Modelo de filtros de pedidos")
@Getter
@Setter
public class OrderFilter {
	
	@ApiModelProperty(example = "1", position = 10)
	private Long customerId;
	
	@ApiModelProperty(example = "1", position = 20)
	private Long restaurantId;
	
	@ApiModelProperty(example = "2020-01-31T22:02:56.771Z", position = 30)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime startCreationDate;
	
	@ApiModelProperty(example = "2020-01-31T23:00:56.771Z", position = 40)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime endCreationDate;

}
