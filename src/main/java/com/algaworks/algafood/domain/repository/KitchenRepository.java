package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Kitchen;

@Repository
public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long> {	
	
	List<Kitchen> findAllByNameContaining(String name);
	
	Optional<Kitchen> findByName(String name);
	
	Boolean existsByName(String name);

}
