package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "grupo")
public class Group {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao", 
	        joinColumns = @JoinColumn(name = "grupo_id"), 
	        inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private Set<Permission> permissions = new HashSet<>();
	
	public boolean disassociatePermission(Permission permission) {
		return this.getPermissions().remove(permission);
	}
	
	public boolean associatePermission(Permission permission) {
		return this.getPermissions().add(permission);
	}

}
