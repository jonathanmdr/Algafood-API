package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>, ProductRepositoryQueries {
	
	List<Product> findByRestaurant(Restaurant restaurant);
	
	@Query("from Product p where p.active = true and p.restaurant = :restaurant")
	List<Product> findActiveByRestaurant(Restaurant restaurant);
	
	@Query("from Product where restaurant.id = :restaurantId and id = :productId")
	Optional<Product> findById(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

}
