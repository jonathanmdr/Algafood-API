package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Permission;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long> {

}
