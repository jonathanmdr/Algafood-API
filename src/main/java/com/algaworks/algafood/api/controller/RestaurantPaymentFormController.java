package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.controller.openapi.controller.RestaurantPaymentFormControllerOpenApi;
import com.algaworks.algafood.api.mapper.PaymentFormMapper;
import com.algaworks.algafood.api.model.PaymentFormDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/payment-forms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentFormController implements RestaurantPaymentFormControllerOpenApi {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private PaymentFormMapper paymentFormMapper;
	
	@GetMapping
	public List<PaymentFormDTO> findAll(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		return paymentFormMapper.toCollectionDto(restaurant.getPaymentForms());
	}
	
	@PutMapping("/{paymentFormId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void associatePaymentForm(@PathVariable Long restaurantId, @PathVariable Long paymentFormId) {
		restaurantService.associatePaymentForm(restaurantId, paymentFormId);
	}
	
	@DeleteMapping("/{paymentFormId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void disassociatePaymentForm(@PathVariable Long restaurantId, @PathVariable Long paymentFormId) {
		restaurantService.disassociatePaymentForm(restaurantId, paymentFormId);
	}		

}
