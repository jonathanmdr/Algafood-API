package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.PaymentFormMapper;
import com.algaworks.algafood.api.model.PaymentFormDTO;
import com.algaworks.algafood.api.model.input.PaymentFormInput;
import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.service.PaymentFormService;

@RestController
@RequestMapping("/payment-forms")
public class PaymentFormController {
	
	@Autowired
	private PaymentFormService paymentFormService;
	
	@Autowired
	private PaymentFormMapper paymentFormMapper;
	
	@GetMapping
	public List<PaymentFormDTO> findAll() {
		return paymentFormMapper.toCollectionDto(paymentFormService.findAll());
	}
	
	@GetMapping("/{paymentFormId}")
	public PaymentFormDTO findById(@PathVariable Long paymentFormId) {
		return paymentFormMapper.toDto(paymentFormService.findById(paymentFormId));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PaymentFormDTO save(@RequestBody @Valid PaymentFormInput paymentFormInput) {
		PaymentForm paymentForm = paymentFormMapper.toDomainObject(paymentFormInput);
		return paymentFormMapper.toDto(paymentFormService.save(paymentForm));
	}
	
	@PutMapping("/{paymentFormId}")
	public PaymentFormDTO update(@PathVariable Long paymentFormId, @RequestBody @Valid PaymentFormInput paymentFormInput) {
		PaymentForm paymentForm = paymentFormService.findById(paymentFormId);
			
		paymentFormMapper.copyToDomainObject(paymentFormInput, paymentForm);
			
		return paymentFormMapper.toDto(paymentFormService.save(paymentForm));
	}
	
	@DeleteMapping("/{paymentFormId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long paymentFormId) {
		paymentFormService.delete(paymentFormId);
	}

}
