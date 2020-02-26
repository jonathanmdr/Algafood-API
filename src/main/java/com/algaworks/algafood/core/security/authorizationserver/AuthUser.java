package com.algaworks.algafood.core.security.authorizationserver;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.algaworks.algafood.domain.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser extends org.springframework.security.core.userdetails.User {
		
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String fullName;
	
	public AuthUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		
		this.userId = user.getId();
		this.fullName = user.getName();
	}

}
