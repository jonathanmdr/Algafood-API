package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.GroupNotFoundException;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.repository.GroupRepository;

@Service
public class GroupService {
	
	private static final String MESSAGE_GROUP_CONFLICT = "Grupo de ID: %d não pode ser excluído, pois está em uso!";
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Transactional(readOnly = true)
	public List<Group> findAll() {
		return groupRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Group findById(Long id) {
		return groupRepository.findById(id)
				.orElseThrow(() -> new GroupNotFoundException(id));
	}
	
	@Transactional
	public Group save(Group group) {
		return groupRepository.save(group);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			groupRepository.deleteById(id);
			groupRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new GroupNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_GROUP_CONFLICT, id));
		}
	}

}
