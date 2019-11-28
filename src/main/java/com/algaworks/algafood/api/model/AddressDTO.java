package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
	
	private String zipCode;
	private String publicPlace;
	private String number;
	private String complement;
	private String neighborhood;	
	private CitySummaryDTO city;

}
