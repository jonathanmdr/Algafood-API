package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.OrderStatus;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQuerySevice;

@Repository
public class SaleQueryServiceImpl implements SaleQuerySevice {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<DailySale> findDailySales(DailySaleFilter filter, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(DailySale.class);
		var root = query.from(Order.class);
		var predicates = new ArrayList<Predicate>();
		
		var functionConvertTzCreationDate = builder.function("convert_tz", Date.class, root.get("creationDate"), builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDateCreationDate = builder.function("date", Date.class, functionConvertTzCreationDate);			
		
		var selection = builder.construct(DailySale.class, 
				functionDateCreationDate,
				builder.count(root.get("id")),
				builder.sum(root.get("amount")));
		
		if (filter.getRestaurantId() != null) {
			predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}
		
		if (filter.getStartCreationDate() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getStartCreationDate()));
		}
		
		if (filter.getEndCreationDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getEndCreationDate()));
		}
		
		predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));								
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateCreationDate);
				
		return manager.createQuery(query).getResultList();
	}
	
}
