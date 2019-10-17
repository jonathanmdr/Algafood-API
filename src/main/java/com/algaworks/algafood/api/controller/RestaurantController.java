package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping
	public List<Restaurant> findAll() {
		return restaurantRepository.list();
	}
	
	@GetMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> findById(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId);
		return restaurant != null ? ResponseEntity.ok(restaurant) : ResponseEntity.notFound().build();
	}
	
	@PostMapping	
	public ResponseEntity<?> save(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantService.save(restaurant);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant restaurantSaved = restaurantRepository.findById(restaurantId);
		
		if (restaurantSaved != null) {
			BeanUtils.copyProperties(restaurant, restaurantSaved, "id");
			restaurantSaved =  restaurantService.save(restaurantSaved);
			return ResponseEntity.ok(restaurantSaved);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> delete(@PathVariable Long restaurantId) {
		try {
			restaurantService.delete(restaurantId);
			return ResponseEntity.noContent().build();
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(EntityInUseException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PatchMapping("/retaurantId")
	public ResponseEntity<?> updatePartial(@PathVariable Long restaurantId, @RequestBody Map<String, Object> values) {
		Restaurant restaurantSaved = restaurantRepository.findById(restaurantId);
		
		if (restaurantSaved == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(values, restaurantSaved);
		
		return update(restaurantId, restaurantSaved);
	}
	
	private void merge(Map<String, Object> valuesOrigin, Restaurant restaurantDestiny) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurant restaurantOrigin = objectMapper.convertValue(valuesOrigin, Restaurant.class);
		
		valuesOrigin.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, key);
			field.setAccessible(true);
			
			Object newValue = ReflectionUtils.getField(field, restaurantOrigin);
			
			ReflectionUtils.setField(field, restaurantDestiny, newValue);
		});
	}

}
