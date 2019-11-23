package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.UserNotFoundException;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;

@Service
public class UserService {
	
	private static final String MESSAGE_USER_CONFLICT = "Usuário de ID: %d não pode ser excluído, pois está em uso!";
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
	}
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public void updatePassword(Long id, String currentPassword, String newPassword) {
		User user = findById(id);
		
		if (user.passwordDoesNotMatch(currentPassword)) {
			throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		user.setPassword(newPassword);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException ex) {
			throw new UserNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_USER_CONFLICT, id));
		}
	}

}
