package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;

public class OrderSpecs {
	
	public static Specification<Order> usingFilter(OrderFilter filter) {
		return (root, query, builder) -> {
			if (Order.class.equals(query.getResultType())) {
				root.fetch("restaurant").fetch("kitchen");
				root.fetch("customer");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getCustomerId() != null) {
				predicates.add(builder.equal(root.get("customer"), filter.getCustomerId()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if (filter.getStartCreationDate() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getStartCreationDate()));
			}
			
			if (filter.getStartCreationDate() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getEndCreationDate()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
