package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.UserNotFoundException;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;

@Service
public class UserService {
	
	private static final String MESSAGE_USER_CONFLICT = "Usuário de ID: %d não pode ser excluído, pois está em uso!";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupService groupService;
	
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
		userRepository.detach(user);
		
		Optional<User> userExistent = userRepository.findByEmail(user.getEmail());
		
		if (userExistent.isPresent() && !userExistent.get().equals(user)) {
			throw new BusinessException(String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));
		}
		
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
	
	@Transactional
	public void disassociateGroup(Long userId, Long groupId) {
		User user = findById(userId);
		Group group = groupService.findById(groupId);
		
		user.disassociateGroup(group);
	}
	
	@Transactional
	public void associateGroup(Long userId, Long groupId) {
		User user = findById(userId);
		Group group = groupService.findById(groupId);
		
		user.associateGroup(group);
	}

}
