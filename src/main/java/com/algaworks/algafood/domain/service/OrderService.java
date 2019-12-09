package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.OrderNotFoundException;
import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.infrastructure.repository.spec.OrderSpecs;

@Service
public class OrderService {
	
	private static final String MESSAGE_RESTAURANT_NOT_ACCEPTABLE_PAYMENT_FORM = "Forma de pagamento '%s' não é aceita por esse restaurante.";
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private PaymentFormService paymentFormService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Transactional(readOnly = true)
	public Page<Order> findAll(OrderFilter orderFilter, Pageable pageable) {
		return orderRepository.findAll(OrderSpecs.usingFilter(orderFilter), pageable);
	}
	
	@Transactional(readOnly = true)
	public Order findByCode(String code) {
		return orderRepository.findByCode(code)
				.orElseThrow(() -> new OrderNotFoundException(code));
	}
	
	@Transactional
	public Order save(Order order) {
		validateOrder(order);
		validateOrderItems(order);
		
		order.setFreightRate(order.getRestaurant().getFreightRate());
		order.calculateAmount();
		
		return orderRepository.save(order);
	}
	
	private void validateOrder(Order order) {
		Restaurant restaurant = restaurantService.findById(order.getRestaurant().getId());
		PaymentForm paymentForm = paymentFormService.findById(order.getPaymentForm().getId());
		City city = cityService.findById(order.getAddress().getCity().getId());
		User customer = userService.findById(order.getCustomer().getId());
		
		order.setRestaurant(restaurant);
		order.setPaymentForm(paymentForm);
		order.getAddress().setCity(city);
		order.setCustomer(customer);
		
		if (restaurant.notAcceptPaymentForm(paymentForm)) {
			throw new BusinessException(String.format(MESSAGE_RESTAURANT_NOT_ACCEPTABLE_PAYMENT_FORM, paymentForm.getName()));
		}
	}
	
	private void validateOrderItems(Order order) {
		order.getItems().forEach(orderItem -> {
			Product product = productService.findById(order.getRestaurant().getId(), orderItem.getProduct().getId());
			
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setUnitPrice(product.getPrice());
		});
	}

}
