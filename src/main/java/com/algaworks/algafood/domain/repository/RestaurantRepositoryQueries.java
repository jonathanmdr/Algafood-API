package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {

	List<Restaurant> findByNameAndFreightRate(String name, BigDecimal freightRateInitial, BigDecimal freightRateEnd);

}