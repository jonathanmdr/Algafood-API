package com.algaworks.algafood.api.controller.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.api.model.input.RestaurantInput;

import io.swagger.annotations.Api;

@Api(tags = "Restaurantes")
public interface RestaurantControllerOpenApi {

	public List<RestaurantDTO> findAllSummary();
	
	public List<RestaurantDTO> findAllJustName();
	
	public RestaurantDTO findById(Long restaurantId);
	
	public RestaurantDTO save(RestaurantInput restaurantInput);
	
	public RestaurantDTO update(Long restaurantId, RestaurantInput restaurantInput);
	
	public void activate(Long restaurantId);
	
	public void activations(List<Long> restaurantIds);
	
	public void open(Long restaurantId);
	
	public void close(Long restaurantId);
	
	public void delete(Long restaurantId);
	
	public void inactivate(Long restaurantId);
	
	public void inactivations(List<Long> restaurantIds);
		
	public RestaurantDTO updatePartial(Long restaurantId, Map<String, Object> values, HttpServletRequest request);
	
}
