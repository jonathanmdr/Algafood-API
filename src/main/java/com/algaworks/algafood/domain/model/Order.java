package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.exception.BusinessException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Order {
	
	private static final String MESSAGE_UPDATE_STATUS_NOT_ACCEPTABLE = "Status do pedido %d não pode ser alterado de '%s' para '%s'.";
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "subtotal")
	private BigDecimal subTotal;
	
	@Column(name = "taxa_frete")
	private BigDecimal freightRate;
	
	@Column(name = "valor_total")
	private BigDecimal amount;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.CREATED;
	
	@CreationTimestamp
	@Column(name = "data_criacao")
	private OffsetDateTime creationDate;
	
	@Column(name = "data_confirmacao")
	private OffsetDateTime confirmatedDate;
	
	@Column(name = "data_cancelamento")
	private OffsetDateTime canceledDate;
	
	@Column(name = "data_entrega")
	private OffsetDateTime deliveredDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private PaymentForm paymentForm;
	
	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private User customer;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> items = new ArrayList<>();
	
	private void setStatus(OrderStatus newStatus) {
		if (this.getStatus().cannotChangeTo(newStatus)) {
			throw new BusinessException(String.format(MESSAGE_UPDATE_STATUS_NOT_ACCEPTABLE, this.getId(), this.getStatus().getDescription(), newStatus.getDescription()));
		}
		
		this.status = newStatus;
	}
	
	public void calculateAmount() {
		this.getItems().forEach(OrderItem::calculateTotalPrice);
		
		this.subTotal = this.getItems().stream()
				.map(item -> item.getTotalPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.amount = this.subTotal.add(this.freightRate);
	}
	
	public void confirm() {
		this.setStatus(OrderStatus.CONFIRMED);
		this.setConfirmatedDate(OffsetDateTime.now());
	}
	
	public void deliver() {
		this.setStatus(OrderStatus.DELIVERED);
		this.setDeliveredDate(OffsetDateTime.now());
	}
	
	public void cancel() {
		this.setStatus(OrderStatus.CANCELED);
		this.setCanceledDate(OffsetDateTime.now());
	}

}
