package com.algaworks.algafood.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CityController;
import com.algaworks.algafood.api.controller.GroupController;
import com.algaworks.algafood.api.controller.GroupPermissionController;
import com.algaworks.algafood.api.controller.KitchenController;
import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.controller.OrderStatusController;
import com.algaworks.algafood.api.controller.PaymentFormController;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.controller.RestaurantPaymentFormController;
import com.algaworks.algafood.api.controller.RestaurantProductController;
import com.algaworks.algafood.api.controller.RestaurantProductPhotoController;
import com.algaworks.algafood.api.controller.RestaurantUserManagerController;
import com.algaworks.algafood.api.controller.StateController;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.controller.UserGroupController;

@Component
public class AlgaLinks {

	public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
			new TemplateVariable("projection", VariableType.REQUEST_PARAM));

	public Link linkToOrders(String linkRelation) {
		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("customerId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
				new TemplateVariable("startCreationDate", VariableType.REQUEST_PARAM),
				new TemplateVariable("endCreationDate", VariableType.REQUEST_PARAM));

		return new Link(UriTemplate.of(linkTo(OrderController.class)
				.toUri().toString(), PAGINATION_VARIABLES.concat(filterVariables)), linkRelation);
	}
	
	public Link linkToConfirmOrder(String orderCode, String linkRelation) {
		return linkTo(methodOn(OrderStatusController.class)
				.confirm(orderCode)).withRel(linkRelation);
	}
	
	public Link linkToDeliverOrder(String orderCode, String linkRelation) {
		return linkTo(methodOn(OrderStatusController.class)
				.deliver(orderCode)).withRel(linkRelation);
	}
	
	public Link linkToCancelOrder(String orderCode, String linkRelation) {
		return linkTo(methodOn(OrderStatusController.class)
				.cancel(orderCode)).withRel(linkRelation);
	}

	public Link linkToRestaurant(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantController.class)
				.findById(restaurantId)).withRel(linkRelation);
	}

	public Link linkToRestaurant(Long restaurantId) {
		return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurants(String linkRelation) {
		return new Link(UriTemplate.of(linkTo(RestaurantController.class)
				.toUri().toString(), PROJECTION_VARIABLES), linkRelation);
	}
	
	public Link linkToRestaurants() {
		return linkToRestaurants(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantPaymentForms(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantPaymentFormController.class)
				.findAllByRestaurantId(restaurantId)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantPaymentForms(Long restaurantId) {
		return linkTo(methodOn(RestaurantPaymentFormController.class)
				.findAllByRestaurantId(restaurantId)).withRel(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantPaymentFormsAssociate(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantPaymentFormController.class)
				.associatePaymentForm(restaurantId, null)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantPaymentFormsDisassociate(Long restaurantId, Long paymentFormId, String linkRelation) {
		return linkTo(methodOn(RestaurantPaymentFormController.class)
				.disassociatePaymentForm(restaurantId, paymentFormId)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantOpening(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantController.class)
				.open(restaurantId)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantClosing(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantController.class)
				.close(restaurantId)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantActivation(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantController.class)
				.activate(restaurantId)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantInactivation(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantController.class)
				.inactivate(restaurantId)).withRel(linkRelation);
	}

	public Link linkToUser(Long userId, String linkRelation) {
		return linkTo(methodOn(UserController.class)
				.findById(userId)).withRel(linkRelation);
	}

	public Link linkToUser(Long userId) {
		return linkToUser(userId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsers(String linkRelation) {
		return linkTo(UserController.class).withRel(linkRelation);
	}

	public Link linkToUsers() {
		return linkToUsers(IanaLinkRelations.SELF.value());
	}

	public Link linkToUserGroup(Long userId, String linkRelation) {
		return linkTo(methodOn(UserGroupController.class)
				.findAllByUserId(userId)).withRel(linkRelation);
	}

	public Link linkToUserGroup(Long userId) {
		return linkToUser(userId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGroups(String linkRelation) {
		return linkTo(methodOn(GroupController.class)
				.findAll()).withRel(linkRelation);
	}
	
	public Link linkToGroups() {
		return linkToGroups(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGroupPermissions(Long groupId, String linkRelation) {
		return linkTo(methodOn(GroupPermissionController.class)
				.findById(groupId)).withRel(linkRelation);
	}

	public Link linkToRestaurantUserManager(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantUserManagerController.class)
				.findAllByRestaurantId(restaurantId)).withRel(linkRelation);
	}

	public Link linkToRestaurantUserManager(Long restaurantId) {
		return linkToRestaurantUserManager(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantUserManagerAssociate(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantUserManagerController.class)
				.associateUser(restaurantId, null)).withRel(linkRelation);
	}
	
	public Link linkToRestaurantUserManagerDisassociate(Long restaurantId, Long userId, String linkRelation) {
		return linkTo(methodOn(RestaurantUserManagerController.class)
				.disassociateUser(restaurantId, userId)).withRel(linkRelation);
	}

	public Link linkToPaymentForm(Long paymentFormId, String linkRelation) {
		return linkTo(methodOn(PaymentFormController.class)
				.findById(paymentFormId, null)).withRel(linkRelation);
	}

	public Link linkToPaymentForm(Long paymentFormId) {
		return linkToPaymentForm(paymentFormId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToPaymentForms(String linkRelation) {
		return linkTo(PaymentFormController.class).withRel(linkRelation);
	}
	
	public Link linkToPaymentForms() {
		return linkTo(PaymentFormController.class).withRel(IanaLinkRelations.SELF.value());
	}

	public Link linkToCity(Long cityId, String linkRelation) {
		return linkTo(methodOn(CityController.class)
				.findById(cityId)).withRel(linkRelation);
	}

	public Link linkToCity(Long cityId) {
		return linkToCity(cityId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCities(String linkRelation) {
		return linkTo(CityController.class).withRel(linkRelation);
	}

	public Link linkToCities() {
		return linkToCities(IanaLinkRelations.SELF.value());
	}

	public Link linkToState(Long stateId, String linkRelation) {
		return linkTo(methodOn(StateController.class)
				.findById(stateId)).withRel(linkRelation);
	}

	public Link linkToState(Long stateId) {
		return linkToState(stateId, IanaLinkRelations.SELF.value());
	}

	public Link linkToStates(String linkRelation) {
		return linkTo(StateController.class).withRel(linkRelation);
	}

	public Link linkToStates() {
		return linkToStates(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduct(Long restaurantId, Long productId, String linkRelation) {
		return linkTo(methodOn(RestaurantProductController.class)
				.findById(restaurantId, productId)).withRel(linkRelation);
	}

	public Link linkToProduct(Long restaurantId, Long productId) {
		return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToProdutcs(Long restaurantId, String linkRelation) {
		return linkTo(methodOn(RestaurantProductController.class)
				.findByRestaurant(restaurantId, null)).withRel(linkRelation);
	}
	
	public Link linkToProdutcs(Long restaurantId) {
		return linkToProdutcs(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToProductPhoto(Long restaurantId, Long productId, String linkRelation) {
		return linkTo(methodOn(RestaurantProductPhotoController.class)
				.findById(restaurantId, productId)).withRel(linkRelation);
	}
	
	public Link linkToProductPhoto(Long restaurantId, Long productId) {
		return linkToProductPhoto(restaurantId, productId, IanaLinkRelations.SELF.value());
	}

	public Link linkToKitchen(Long kitchenId, String linkRelation) {
		return linkTo(methodOn(KitchenController.class)
				.findById(kitchenId)).withRel(linkRelation);
	}

	public Link linkToKitchen(Long kitchenId) {
		return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToKitchens(String linkRelation) {
		return linkTo(KitchenController.class).withRel(linkRelation);
	}

	public Link linkToKitchens() {
		return linkToKitchens(IanaLinkRelations.SELF.value());
	}

}
