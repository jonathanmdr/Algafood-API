package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurant> findByNameAndFreightRate(String name, BigDecimal freightRateInitial, BigDecimal freightRateEnd) {
		var jpql = new StringBuilder(" from Restaurant where 1 = 1 ");		
		var parameters = new HashMap<String, Object>();
		
		if (StringUtils.hasLength(name)) {
			jpql.append(" and name like :name ");
			parameters.put("name", "%"+ name +"%");
		}
		
		if (freightRateInitial != null) {
			jpql.append(" and freightRate >= :freightRateInitial ");
			parameters.put("freightRateInitial", freightRateInitial);
		}
		
		if (freightRateEnd != null) {
			jpql.append(" and freightRate <= :freightRateEnd ");
			parameters.put("freightRateEnd", freightRateEnd);
		}
		
		TypedQuery<Restaurant> query = manager.createQuery(jpql.toString(), Restaurant.class);
		
		parameters.forEach((key, value) -> query.setParameter(key, value));
		
		return query.getResultList();
	}

}
