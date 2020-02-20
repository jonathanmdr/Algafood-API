package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Component
public class AlgaSecurity {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * Como estamos usando JWT na aplicação o getPrincipal() retorna uma instância
	 * de JWT o que nos permite fazer o casting e trabalhar com os clains do token.
	 * 
	 * @return ID do usuário no contexto atual.
	 */
	public Long getUserId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("user_id");
	}

	/**
	 * Usado nas anotações @Security para validação personalizada para regras de
	 * negócio.
	 * 
	 * @param restaurantId ID do restaurante atual indicado no path da requisição.
	 * @return retorna true caso o usuário do contexto atual seja um responsável
	 *         pelo restaurante e retorna falso caso o mesmo não seja.
	 */
	public boolean isUserManager(Long restaurantId) {
		return restaurantRepository.existsUserManager(restaurantId, getUserId());
	}

}
