package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Group;

@Repository
public interface GroupRepository extends CustomJpaRepository<Group, Long> {

}
