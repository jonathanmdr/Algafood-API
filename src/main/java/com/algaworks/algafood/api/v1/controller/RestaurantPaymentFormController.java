package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.RestaurantPaymentFormControllerOpenApi;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.mapper.PaymentFormMapper;
import com.algaworks.algafood.api.v1.model.PaymentFormDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/payment-forms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentFormController implements RestaurantPaymentFormControllerOpenApi {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private PaymentFormMapper paymentFormMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<PaymentFormDTO> findAllByRestaurantId(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);

		CollectionModel<PaymentFormDTO> paymentFormsDto = paymentFormMapper
				.toCollectionModel(restaurant.getPaymentForms())
				.removeLinks()
				.add(algaLinks.linkToRestaurantPaymentForms(restaurantId))
				.add(algaLinks.linkToRestaurantPaymentFormsAssociate(restaurantId, "associate"));

		paymentFormsDto.getContent().forEach(paymentForm -> {
			paymentForm.add(algaLinks.linkToRestaurantPaymentFormsDisassociate(restaurantId, paymentForm.getId(), "disassociate"));
		});

		return paymentFormsDto;
	}

	@Override
	@PutMapping("/{paymentFormId}")
	public ResponseEntity<Void> associatePaymentForm(@PathVariable Long restaurantId, @PathVariable Long paymentFormId) {
		restaurantService.associatePaymentForm(restaurantId, paymentFormId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{paymentFormId}")
	public ResponseEntity<Void> disassociatePaymentForm(@PathVariable Long restaurantId, @PathVariable Long paymentFormId) {
		restaurantService.disassociatePaymentForm(restaurantId, paymentFormId);

		return ResponseEntity.noContent().build();
	}

}
