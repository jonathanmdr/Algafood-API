package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Order;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {
	
	@Query("from Order o join fetch o.customer join fetch o.restaurant r join fetch r.kitchen")
	List<Order> findAll();

}
