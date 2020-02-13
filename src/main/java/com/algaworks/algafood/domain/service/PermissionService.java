package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.PermissionNotFoundException;
import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.PermissionRepository;

@Service
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Transactional(readOnly = true)
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Permission findById(Long id) {
		return permissionRepository.findById(id)
				.orElseThrow(() -> new PermissionNotFoundException(id));
	}

}
