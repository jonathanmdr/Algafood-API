package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "foto_produto")
public class ProductPhoto {
		
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long id;
	
	// TODO: Rever problema de nomenclatura do field em inglês no hibernate
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Product produto;
	
	@Column(name = "nome_arquivo")
	private String fileName;
	
	@Column(name = "descricao")
	private String description;
		
	private String contentType;
	
	@Column(name = "tamanho")
	private Long size;
	
	public Long getRestaurantId() {
		if (produto != null) {
			return produto.getRestaurant().getId();
		}
		
		return null;
	}

}
