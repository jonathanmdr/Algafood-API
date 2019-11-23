package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurante")
public class Restaurant {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String name;
	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal freightRate;
		
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Kitchen kitchen;
	
	@Embedded
	private Address address;
	
	@Column(name = "ativo")
	private Boolean active = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(name = "data_criacao", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime createdDate;
	
	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updatedDate;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	        joinColumns = @JoinColumn(name = "restaurante_id"), 
	        inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<PaymentForm> paymentForms = new ArrayList<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	public void activate() {
		setActive(Boolean.TRUE);
	}
	
	public void inactivate() {
		setActive(Boolean.FALSE);
	}

}
