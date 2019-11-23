package com.algaworks.algafood.api.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PaymentFormDTO;
import com.algaworks.algafood.api.model.input.PaymentFormInput;
import com.algaworks.algafood.domain.model.PaymentForm;

@Component
public class PaymentFormMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PaymentFormDTO toDto(PaymentForm paymentForm) {
		return modelMapper.map(paymentForm, PaymentFormDTO.class);
	}
	
	public List<PaymentFormDTO> toCollectionDto(Collection<PaymentForm> paymentForms) {
		return paymentForms.stream().map(paymentForm -> toDto(paymentForm)).collect(Collectors.toList());
	}
	
	public PaymentForm toDomainObject(PaymentFormInput paymentFormInput) {
		return modelMapper.map(paymentFormInput, PaymentForm.class);
	}
	
	public void copyToDomainObject(PaymentFormInput paymentFormInput, PaymentForm paymentForm) {
		modelMapper.map(paymentFormInput, paymentForm);
	}

}
