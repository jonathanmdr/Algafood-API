package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.City;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long> {

}
