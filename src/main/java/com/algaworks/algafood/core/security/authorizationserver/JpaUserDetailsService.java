package com.algaworks.algafood.core.security.authorizationserver;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail informado!"));
		
		return new AuthUser(user, getAuthorities(user));
	}
	
	private Collection<GrantedAuthority> getAuthorities(User user) {
		return user.getGroups().stream()
				.flatMap(group -> group.getPermissions().stream())
				.map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
				.collect(Collectors.toSet());
	}
	
}
