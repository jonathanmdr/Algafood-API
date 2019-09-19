package com.algaworks.algafood.di.model;

public class Customer {
	
	private String name;
	private String email;
	private String phone;
	private Boolean active = Boolean.FALSE;
	
	public Customer(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void activate() {
		this.active = Boolean.TRUE;
	}

}
