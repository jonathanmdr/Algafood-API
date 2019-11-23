package com.algaworks.algafood.api.model;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private String password;

}
