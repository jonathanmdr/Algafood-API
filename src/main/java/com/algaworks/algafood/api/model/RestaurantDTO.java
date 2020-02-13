package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@ApiModel(value = "Resturante", description = "Representação de restaurante")
@Getter
@Setter
public class RestaurantDTO extends RepresentationModel<RestaurantDTO> {
	
	@ApiModelProperty(example = "1", position = 10)
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet", position = 20)
	private String name;
	
	@ApiModelProperty(example = "12.00", position = 30)
	private BigDecimal freightRate;
	
	@ApiModelProperty(position = 40)
	private KitchenDTO kitchen;
	
	@ApiModelProperty(position = 50)
	private Boolean active;
	
	@ApiModelProperty(position = 60)
	private Boolean opened;
	
	@ApiModelProperty(position = 70)
	private AddressDTO address;

}
