package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.PaymentForm;
import com.algaworks.algafood.domain.repository.PaymentFormRepository;

@Component
public class PaymentFormRepositoryImpl implements PaymentFormRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<PaymentForm> list() {
		return manager.createQuery("from PaymentForm", PaymentForm.class).getResultList();
	}

	@Override
	public PaymentForm findById(Long id) {
		return manager.find(PaymentForm.class, id);
	}

	@Override
	public PaymentForm save(PaymentForm paymentForm) {
		return manager.merge(paymentForm);
	}

	@Override
	public void delete(PaymentForm paymentForm) {
		paymentForm = findById(paymentForm.getId());
		manager.remove(paymentForm);
	}

}
