package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	private static final String MESSAGE_RESTAURANT_CONFLICT = "Restaurante de ID: %d não pode ser excluído, pois está em uso!";
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private KitchenService kitchenService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private PaymentFormService paymentFormService;
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly = true)
	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Restaurant findById(Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(id));
	}
	
	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Long cityId = restaurant.getAddress().getCity().getId();
		
		Kitchen kitchen = kitchenService.findById(kitchenId);			
		City city = cityService.findById(cityId);
		
		restaurant.setKitchen(kitchen);
		restaurant.getAddress().setCity(city);
		
		return restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void activate(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.activate();
	}
	
	@Transactional
	public void inactivate(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.inactivate();
	}
	
	@Transactional
	public void activate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::activate);
	}
	
	@Transactional
	public void inactivate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::inactivate);
	}
	
	@Transactional
	public void opening(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.opening();
	}
	
	@Transactional
	public void closing(Long id) {
		Restaurant restaurant = findById(id);
		restaurant.closing();
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			restaurantRepository.deleteById(id);
			restaurantRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new RestaurantNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_RESTAURANT_CONFLICT, id));
		}
	}
	
	@Transactional
	public void disassociatePaymentForm(Long restaurantId, Long paymentFormId) {
		Restaurant restaurant = findById(restaurantId);
		PaymentForm paymentForm = paymentFormService.findById(paymentFormId);
		
		restaurant.disassociatePaymentForm(paymentForm);
	}
	
	@Transactional
	public void associatePaymentForm(Long restaurantId, Long paymentFormId) {
		Restaurant restaurant = findById(restaurantId);
		PaymentForm paymentForm = paymentFormService.findById(paymentFormId);
		
		restaurant.associatePaymentForm(paymentForm);
	}
	
	@Transactional
	public void disassociateUser(Long restaurantId, Long userId) {
		Restaurant restaurant = findById(restaurantId);
		User user = userService.findById(userId);
		
		restaurant.disassociateUser(user);
	}
	
	@Transactional
	public void associateUser(Long restaurantId, Long userId) {
		Restaurant restaurant = findById(restaurantId);
		User user = userService.findById(userId);
		
		restaurant.associateUser(user);
	}

}
