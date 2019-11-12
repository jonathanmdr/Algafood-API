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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Order {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal subTotal;
	
	@Column(name = "taxa_frete")
	private BigDecimal freightRate;
	
	@Column(name = "valor_total")
	private BigDecimal amount;
	
	@Embedded
	private Address address;
	
	private OrderStatus status;
	
	@CreationTimestamp
	@Column(name = "data_criacao")
	private LocalDateTime creationDate;
	
	@Column(name = "data_confirmacao")
	private LocalDateTime confirmatedDate;
	
	@Column(name = "data_cancelamento")
	private LocalDateTime canceledDate;
	
	@Column(name = "data_entrega")
	private LocalDateTime deliveredDate;
	
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private PaymentForm paymentForm;
	
	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private User customer;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> items = new ArrayList<>();

}
