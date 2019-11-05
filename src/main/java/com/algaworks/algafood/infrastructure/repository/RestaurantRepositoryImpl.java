package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreightRate;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withNameLike;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Lazy
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<Restaurant> findByNameAndFreightRateJPQL(String name, BigDecimal freightRateInitial, BigDecimal freightRateEnd) {
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
	
	@Override
	public List<Restaurant> findByNameAndFreightRateCriteria(String name, BigDecimal freightRateInitial, BigDecimal freightRateEnd) {
		var builder = manager.getCriteriaBuilder();
		
		var criteria = builder.createQuery(Restaurant.class);		
		var root = criteria.from(Restaurant.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		
		if (freightRateInitial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("freightRate"), freightRateInitial));
		}
		
		if (freightRateEnd != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("freightRate"), freightRateEnd));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public List<Restaurant> findByNameAndFreightRateFree(String name) {
		return restaurantRepository.findAll(withFreightRate().and(withNameLike(name)));
	}

}
