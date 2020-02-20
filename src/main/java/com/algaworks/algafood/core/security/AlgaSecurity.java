package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * Como estamos usando JWT na aplicação o getPrincipal() retorna uma instância
	 * de JWT o que nos permite fazer o casting e trabalhar com os clains do token.
	 * 
	 * @return ID do usuário no contexto atual
	 */
	public Long getUserId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("user_id");
	}

}
