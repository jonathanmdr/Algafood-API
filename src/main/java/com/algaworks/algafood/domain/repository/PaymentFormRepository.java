package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.PaymentForm;

@Repository
public interface PaymentFormRepository extends CustomJpaRepository<PaymentForm, Long> {

}
