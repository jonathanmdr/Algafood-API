package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.PaymentForm;

@Repository
public interface PaymentFormRepository extends JpaRepository<PaymentForm, Long> {

}
