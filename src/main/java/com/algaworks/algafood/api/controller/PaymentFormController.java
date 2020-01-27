package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.controller.openapi.controller.PaymentFormControllerOpenApi;
import com.algaworks.algafood.api.mapper.PaymentFormMapper;
import com.algaworks.algafood.api.model.PaymentFormDTO;
import com.algaworks.algafood.api.model.input.PaymentFormInput;
import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.service.PaymentFormService;

@RestController
@RequestMapping(path = "/payment-forms", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentFormController implements PaymentFormControllerOpenApi {
	
	@Autowired
	private PaymentFormService paymentFormService;
	
	@Autowired
	private PaymentFormMapper paymentFormMapper;
	
	@GetMapping
	public ResponseEntity<List<PaymentFormDTO>> findAll(ServletWebRequest request) {
		String eTag = generateEtagFilter(request);
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		List<PaymentFormDTO> paymentForms = paymentFormMapper.toCollectionDto(paymentFormService.findAll());		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(paymentForms);
	}
	
	@GetMapping("/{paymentFormId}")
	public ResponseEntity<PaymentFormDTO> findById(@PathVariable Long paymentFormId, ServletWebRequest request) {
		String eTag = generateEtagFilter(request);
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		PaymentFormDTO paymentForm = paymentFormMapper.toDto(paymentFormService.findById(paymentFormId));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(paymentForm);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PaymentFormDTO save(@RequestBody @Valid PaymentFormInput paymentFormInput) {
		PaymentForm paymentForm = paymentFormMapper.toDomainObject(paymentFormInput);
		return paymentFormMapper.toDto(paymentFormService.save(paymentForm));
	}
	
	@PutMapping("/{paymentFormId}")
	public PaymentFormDTO update(@PathVariable Long paymentFormId, @RequestBody @Valid PaymentFormInput paymentFormInput) {
		PaymentForm paymentFormCurrent = paymentFormService.findById(paymentFormId);
			
		paymentFormMapper.copyToDomainObject(paymentFormInput, paymentFormCurrent);
			
		return paymentFormMapper.toDto(paymentFormService.save(paymentFormCurrent));
	}
	
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
