package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Order;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	
	@Query("from Order o join fetch o.customer join fetch o.restaurant r join fetch r.kitchen")
	List<Order> findAll();
	
	Optional<Order> findByCode(String code);
	
	boolean isOrderManagedBy(String code, Long userId);

}
