package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Permission;

public interface PermissionRepository {
	
	List<Permission> list();
	
	Permission findById(Long id);
	
	Permission save(Permission permission);
	
	void delete(Permission permission);

}
