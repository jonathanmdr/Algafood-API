package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.PaymentFormInput;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PaymentFormController;
import com.algaworks.algafood.api.v1.model.PaymentFormDTO;
import com.algaworks.algafood.domain.model.PaymentForm;

@Component
public class PaymentFormMapper extends RepresentationModelAssemblerSupport<PaymentForm, PaymentFormDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public PaymentFormMapper() {
		super(PaymentFormController.class, PaymentFormDTO.class);
	}

	@Override
	public PaymentFormDTO toModel(PaymentForm paymentForm) {
		PaymentFormDTO paymentFormDto = createModelWithId(paymentForm.getId(), paymentForm);
		modelMapper.map(paymentForm, paymentFormDto);

		if (algaSecurity.canConsultingPaymentForms()) {
		    paymentFormDto.add(algaLinks.linkToPaymentForms("paymentForms"));
		}

		return paymentFormDto;
	}

	@Override
	public CollectionModel<PaymentFormDTO> toCollectionModel(Iterable<? extends PaymentForm> entities) {
	    CollectionModel<PaymentFormDTO> paymentFormsModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.canConsultingPaymentForms()) {
	        paymentFormsModel.add(algaLinks.linkToPaymentForms());
	    }
	    
	    return paymentFormsModel;
	}

	public PaymentForm toDomainObject(PaymentFormInput paymentFormInput) {
		return modelMapper.map(paymentFormInput, PaymentForm.class);
	}

	public void copyToDomainObject(PaymentFormInput paymentFormInput, PaymentForm paymentForm) {
		modelMapper.map(paymentFormInput, paymentForm);
	}

}
