package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.PaymentFormNotFoundException;
import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.repository.PaymentFormRepository;

@Service
public class PaymentFormService {
	
	private static final String MESSAGE_PAYMENT_FORM_CONFLICT = "Forma de pagamento de ID: %d não pode ser excluída, pois está em uso!";
	
	@Autowired
	private PaymentFormRepository paymentFormRepository;
	
	@Transactional(readOnly = true)
	public OffsetDateTime getLastUpdatedDate() {
		return paymentFormRepository.getLastUpdatedDate();
	}	
	
	@Transactional(readOnly = true)
	public List<PaymentForm> findAll() {
		return paymentFormRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public PaymentForm findById(Long id) {
		return paymentFormRepository.findById(id)
				.orElseThrow(() -> new PaymentFormNotFoundException(id));
	}
	
	@Transactional
	public PaymentForm save(PaymentForm paymentForm) {
		return paymentFormRepository.save(paymentForm);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			paymentFormRepository.deleteById(id);
			paymentFormRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new PaymentFormNotFoundException(id);
		} catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format(MESSAGE_PAYMENT_FORM_CONFLICT, id));
		}
	}

}
