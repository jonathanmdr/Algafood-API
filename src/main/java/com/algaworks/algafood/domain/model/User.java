package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
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
		
	private String email;
	
	@Column(name = "senha", nullable = false)
	private String password;
	
	@CreationTimestamp
	@Column(name = "data_criacao", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime createdDate;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo", 
	        joinColumns = @JoinColumn(name = "usuario_id"), 
	        inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private Set<Group> groups = new HashSet<>();
	
	public boolean passwordMatch(String password) {
		return this.getPassword().equals(password);
	}
	
	public boolean passwordDoesNotMatch(String password) {
		return !passwordMatch(password);
	}
	
	public boolean disassociateGroup(Group group) {
		return this.getGroups().remove(group);
	}
	
	public boolean associateGroup(Group group) {
		return this.getGroups().add(group);
	}

}
