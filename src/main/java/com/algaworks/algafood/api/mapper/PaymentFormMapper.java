package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PaymentFormController;
import com.algaworks.algafood.api.model.PaymentFormDTO;
import com.algaworks.algafood.api.model.input.PaymentFormInput;
import com.algaworks.algafood.domain.model.PaymentForm;

@Component
public class PaymentFormMapper extends RepresentationModelAssemblerSupport<PaymentForm, PaymentFormDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public PaymentFormMapper() {
		super(PaymentFormController.class, PaymentFormDTO.class);
	}

	@Override
	public PaymentFormDTO toModel(PaymentForm paymentForm) {
		PaymentFormDTO paymentFormDto = createModelWithId(paymentForm.getId(), paymentForm);
		modelMapper.map(paymentForm, paymentFormDto);

		paymentFormDto.add(algaLinks.linkToPaymentForms("payment-forms"));

		return paymentFormDto;
	}

	public PaymentForm toDomainObject(PaymentFormInput paymentFormInput) {
		return modelMapper.map(paymentFormInput, PaymentForm.class);
	}

	public void copyToDomainObject(PaymentFormInput paymentFormInput, PaymentForm paymentForm) {
		modelMapper.map(paymentFormInput, paymentForm);
	}

}
