package com.algaworks.algafood.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
public class User {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String name;
	
	@Column(name = "senha", nullable = false)
	private String password;
	
	@CreationTimestamp
	@Column(name = "data_criacao", nullable = false, columnDefinition = "datetime")
	private LocalDateTime createdDate;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo", 
	        joinColumns = @JoinColumn(name = "usuario_id"), 
	        inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Group> groups = new ArrayList<>();

}
