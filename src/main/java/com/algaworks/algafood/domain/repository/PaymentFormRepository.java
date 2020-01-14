package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.PaymentForm;

@Repository
public interface PaymentFormRepository extends CustomJpaRepository<PaymentForm, Long> {
	
	@Query("select max(updatedDate) from PaymentForm")
	OffsetDateTime getLastUpdatedDate();

}
