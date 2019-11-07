package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreightRate;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withNameLike;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@RestController
@RequestMapping("/tests")
public class TesteController {
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@GetMapping("/kitchens/findFirst")
	public Optional<Kitchen> findFistKitchen() {
		return kitchenRepository.findFirst();
	}
	
	@GetMapping("/kitchens/by-name")
	public List<Kitchen> listByName(String name) {
		return kitchenRepository.findAllByNameContaining(name);
	}
	
	@GetMapping("/kitchens/exists-byName")
	public Boolean existsByName(String name) {
		return kitchenRepository.existsByName(name);
	}
	
	@GetMapping("/restaurants/findFirst")
	public Optional<Restaurant> findFistRestaurant() {
		return restaurantRepository.findFirst();
	}
	
	@GetMapping("/restaurants/by-freightRate")
	public List<Restaurant> listByFreightRate(BigDecimal initValue, BigDecimal endValue) {
		return restaurantRepository.findByFreightRateBetween(initValue, endValue);
	}
	
	@GetMapping("/restaurants/by-nameAndKitchenId")
	public List<Restaurant> listByNameAndKitchenId(String restaurantName, Long kitchenId) {
		return restaurantRepository.findByNameContainingAndKitchenId(restaurantName, kitchenId);
	}
	
	@GetMapping("/restaurants/by-nameFirst")
	public Optional<Restaurant> listByNameFirst(String name) {
		return restaurantRepository.findFirstByNameContaining(name);
	}
	
	@GetMapping("/restaurants/by-nameFirstTwo")
	public List<Restaurant> listByNameFirstTwo(String name) {
		return restaurantRepository.findTop2ByNameContaining(name);
	}
	
	@GetMapping("/restaurants/by-nameAndFreightRate")
	public List<Restaurant> find(String name, BigDecimal freightRateInitial, BigDecimal freightRateEnd) {
		return restaurantRepository.findByNameAndFreightRateJPQL(name, freightRateInitial, freightRateEnd);
	}
	
	@GetMapping("/restaurants/by-nameAndFreightRateCriteria")
	public List<Restaurant> findCriteria(String name, BigDecimal freightRateInitial, BigDecimal freightRateEnd) {
		return restaurantRepository.findByNameAndFreightRateCriteria(name, freightRateInitial, freightRateEnd);
	}
	
	@GetMapping("/restaurants/count-byKitchenId")
	public Long countByKitchenId(Long kitchenId) {
		return restaurantRepository.countByKitchenId(kitchenId);
	}
	
	@GetMapping("/restaurants/by-freightRateFree")
	public List<Restaurant> findCriteriaSDJ(String name) {		
		return restaurantRepository.findByNameAndFreightRateFree(name);
	}

}
