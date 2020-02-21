package com.algaworks.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.controller.openapi.controller.PaymentFormControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.PaymentFormInput;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.api.v1.mapper.PaymentFormMapper;
import com.algaworks.algafood.api.v1.model.PaymentFormDTO;
import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.service.PaymentFormService;

@RestController
@RequestMapping(path = "/v1/payment-forms", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentFormController implements PaymentFormControllerOpenApi {

	@Autowired
	private PaymentFormService paymentFormService;

	@Autowired
	private PaymentFormMapper paymentFormMapper;

	@Override
	@Security.PaymentForms.AllowedConsult
	@GetMapping
	public ResponseEntity<CollectionModel<PaymentFormDTO>> findAll(ServletWebRequest request) {
		String eTag = generateEtagFilter(request);

		if (request.checkNotModified(eTag)) {
			return null;
		}

		var paymentFormsDto = paymentFormMapper.toCollectionModel(paymentFormService.findAll());
		
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(paymentFormsDto);
	}

	@Override
	@Security.PaymentForms.AllowedConsult
	@GetMapping("/{paymentFormId}")
	public ResponseEntity<PaymentFormDTO> findById(@PathVariable Long paymentFormId, ServletWebRequest request) {
		String eTag = generateEtagFilter(request);

		if (request.checkNotModified(eTag)) {
			return null;
		}

		PaymentFormDTO paymentForm = paymentFormMapper.toModel(paymentFormService.findById(paymentFormId));
		
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(paymentForm);
	}

	@Override
	@Security.PaymentForms.AllowedEdit
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PaymentFormDTO save(@RequestBody @Valid PaymentFormInput paymentFormInput) {
		PaymentForm paymentForm = paymentFormMapper.toDomainObject(paymentFormInput);
		return paymentFormMapper.toModel(paymentFormService.save(paymentForm));
	}

	@Override
	@Security.PaymentForms.AllowedEdit
	@PutMapping("/{paymentFormId}")
	public PaymentFormDTO update(@PathVariable Long paymentFormId, @RequestBody @Valid PaymentFormInput paymentFormInput) {
		PaymentForm paymentFormCurrent = paymentFormService.findById(paymentFormId);

		paymentFormMapper.copyToDomainObject(paymentFormInput, paymentFormCurrent);

		return paymentFormMapper.toModel(paymentFormService.save(paymentFormCurrent));
	}

	@Override
	@Security.PaymentForms.AllowedEdit
	@DeleteMapping("/{paymentFormId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long paymentFormId) {
		paymentFormService.delete(paymentFormId);
	}

	private String generateEtagFilter(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		OffsetDateTime lastUpdatedDate = paymentFormService.getLastUpdatedDate();

		if (lastUpdatedDate != null) {
			eTag = String.valueOf(lastUpdatedDate.toEpochSecond());
		}

		return eTag;
	}

}
