package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class Address {
	
	@Column(name = "endereco_cep")
	private String zipCode;
	
	@Column(name = "endereco_logradouro")
	private String publicPlace;
	
	@Column(name = "endereco_numero")
	private String number;
	
	@Column(name = "endereco_complemento")
	private String complement;
	
	@Column(name = "endereco_bairro")
	private String neighborhood;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_cidade_id")
	private City city;

}
