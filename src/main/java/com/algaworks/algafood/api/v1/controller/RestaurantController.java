package com.algaworks.algafood.api.v1.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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

import com.algaworks.algafood.api.v1.controller.openapi.controller.RestaurantControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.RestaurantInput;
import com.algaworks.algafood.api.v1.mapper.RestaurantJustNameMapper;
import com.algaworks.algafood.api.v1.mapper.RestaurantMapper;
import com.algaworks.algafood.api.v1.mapper.RestaurantSummaryMapper;
import com.algaworks.algafood.api.v1.model.RestaurantDTO;
import com.algaworks.algafood.api.v1.model.RestaurantJustNameDTO;
import com.algaworks.algafood.api.v1.model.RestaurantSummaryDTO;
import com.algaworks.algafood.core.validation.ValidationException;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerOpenApi {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private RestaurantMapper restaurantMapper;

	@Autowired
	private RestaurantSummaryMapper restaurantSummaryMapper;

	@Autowired
	private RestaurantJustNameMapper restaurantJustNameMapper;

	@Override
	@GetMapping
	public CollectionModel<RestaurantSummaryDTO> findAllSummary() {
		return restaurantSummaryMapper.toCollectionModel(restaurantService.findAll());
	}

	@Override
	@GetMapping(params = "projection=just-name")
	public CollectionModel<RestaurantJustNameDTO> findAllJustName() {
		return restaurantJustNameMapper.toCollectionModel(restaurantService.findAll());
	}

	@Override
	@GetMapping("/{restaurantId}")
	public RestaurantDTO findById(@PathVariable Long restaurantId) {
		return restaurantMapper.toModel(restaurantService.findById(restaurantId));
	}

	@Override
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public RestaurantDTO save(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantMapper.toDomainObject(restaurantInput);
			return restaurantMapper.toModel(restaurantService.save(restaurant));
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}

	@Override
	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurantCurrent = restaurantService.findById(restaurantId);

			restaurantMapper.copyToDomainObject(restaurantInput, restaurantCurrent);

			return restaurantMapper.toModel(restaurantService.save(restaurantCurrent));
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}

	@Override
	@PutMapping("/{restaurantId}/activate")
	public ResponseEntity<Void> activate(@PathVariable Long restaurantId) {
		restaurantService.activate(restaurantId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activations(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantService.activate(restaurantIds);
		} catch (RestaurantNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	@Override
	@PutMapping("/{restaurantId}/opening")
	public ResponseEntity<Void> open(@PathVariable Long restaurantId) {
		restaurantService.opening(restaurantId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{restaurantId}/closing")
	public ResponseEntity<Void> close(@PathVariable Long restaurantId) {
		restaurantService.closing(restaurantId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{restaurantId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restaurantId) {
		restaurantService.delete(restaurantId);
	}

	@Override
	@DeleteMapping("/{restaurantId}/inactivate")
	public ResponseEntity<Void> inactivate(@PathVariable Long restaurantId) {
		restaurantService.inactivate(restaurantId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/inactivations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivations(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantService.inactivate(restaurantIds);
		} catch (RestaurantNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}

	@Override
	@PatchMapping("/{restaurantId}")
	public RestaurantDTO updatePartial(@PathVariable Long restaurantId, @RequestBody Map<String, Object> values,
			HttpServletRequest request) {
		Restaurant restaurantSaved = restaurantService.findById(restaurantId);

		merge(values, restaurantSaved, request);

		RestaurantInput restaurantInput = restaurantMapper.toInputObject(restaurantSaved);

		validate(restaurantInput, "restaurant");

		return update(restaurantId, restaurantInput);
	}

	private void validate(RestaurantInput restaurantInput, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurantInput, objectName);
		validator.validate(restaurantInput, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
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
		} catch (IllegalArgumentException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
		}
	}

}
