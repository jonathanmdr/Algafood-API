package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurante")
public class Restaurant {
	
	@NotNull(groups = Groups.RestaurantId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@NotBlank
	@Column(name = "nome", nullable = false)
	private String name;
	
	@NotNull
	@PositiveOrZero
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal freightRate;
	
	@Valid
	@NotNull
	@ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Kitchen kitchen;
	
	@JsonIgnore
	@Embedded
	private Address address;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(name = "data_criacao", nullable = false, columnDefinition = "datetime")
	private LocalDateTime createdDate;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false, columnDefinition = "datetime")
	private LocalDateTime updatedDate;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	        joinColumns = @JoinColumn(name = "restaurante_id"), 
	        inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<PaymentForm> paymentForms = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

}
