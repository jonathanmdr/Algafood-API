package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class OrderItem {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "preco_unitario")
	private BigDecimal unitPrice;
	
	@Column(name = "preco_total")
	private BigDecimal totalPrice;
	
	@Column(name = "quantidade")
	private Integer amount;
	
	@Column(name = "observacao")
	private String observation;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Product product;
	
	public void calculateTotalPrice() {
		BigDecimal unitPrice = this.getUnitPrice() == null ? BigDecimal.ZERO : this.getUnitPrice();
		Integer amount = this.getAmount() == null ? 1 : this.getAmount();
		
		this.setTotalPrice(unitPrice.multiply(new BigDecimal(amount)));		
	}

}
