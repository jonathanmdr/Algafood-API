package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries {
	
	List<Restaurant> findByFreightRateBetween(BigDecimal initValue, BigDecimal endValue);
	
	@Query("from Restaurant where name like %:name% and kitchen.id = :id")
	List<Restaurant> findByNameAndKitchenId(@Param("name") String restaurantName, @Param("id") Long kitchenId);
		
	List<Restaurant> findByNameAndKitchenIdXML(String restaurantName, Long kitchenId);
	
	List<Restaurant> findByNameContainingAndKitchenId(String restaurantName, Long kitchenId);
	
	Optional<Restaurant> findFirstByNameContaining(String name);
	
	List<Restaurant> findTop2ByNameContaining(String name);
	
	Long countByKitchenId(Long kitchenId);
	
}
