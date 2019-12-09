package com.algaworks.algafood.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQuerySevice;

@Repository
public class SaleQueryServiceImpl implements SaleQuerySevice {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<DailySale> findDailySales(DailySaleFilter filter) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(DailySale.class);
		var root = query.from(Order.class);
		
		var functionDateCreationDate = builder.function("date", Date.class, root.get("creationDate"));
		
		var selection = builder.construct(DailySale.class, 
				functionDateCreationDate,
				builder.count(root.get("id")),
				builder.sum(root.get("amount")));
		
		query.select(selection);
		query.groupBy(functionDateCreationDate);
				
		return manager.createQuery(query).getResultList();
	}
	
}
