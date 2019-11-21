package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping
	public List<Restaurant> findAll() {
		return restaurantService.findAll();
	}
	
	@GetMapping("/{restaurantId}")
	public Restaurant findById(@PathVariable Long restaurantId) {
		return restaurantService.findById(restaurantId);
	}
	
	@PostMapping	
	public Restaurant save(@RequestBody Restaurant restaurant) {
		try {
			return restaurantService.save(restaurant);
		} catch(EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}")
	public Restaurant update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant restaurantCurrent = restaurantService.findById(restaurantId);
		
		BeanUtils.copyProperties(restaurant, restaurantCurrent, "id", "paymentForms", "address", "createdDate", "products");
		
		try {
			return restaurantService.save(restaurantCurrent);
		} catch(EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@DeleteMapping("/{restaurantId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restaurantId) {
		restaurantService.delete(restaurantId);
	}
	
	@PatchMapping("/{restaurantId}")
	public Restaurant updatePartial(@PathVariable Long restaurantId, @RequestBody Map<String, Object> values, HttpServletRequest request) {
		Restaurant restaurantSaved = restaurantService.findById(restaurantId);
		
		merge(values, restaurantSaved, request);
		
		return update(restaurantId, restaurantSaved);
	}
	
	private void merge(Map<String, Object> valuesOrigin, Restaurant restaurantDestiny, HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurant restaurantOrigin = objectMapper.convertValue(valuesOrigin, Restaurant.class);
			
			valuesOrigin.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Restaurant.class, key);
				field.setAccessible(true);
				
				Object newValue = ReflectionUtils.getField(field, restaurantOrigin);
				
				ReflectionUtils.setField(field, restaurantDestiny, newValue);
			});
		} catch(IllegalArgumentException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
		}
	}

}
