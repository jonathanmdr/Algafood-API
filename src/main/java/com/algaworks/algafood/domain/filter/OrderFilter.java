package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFilter {
	
	private Long customerId;
	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime startCreationDate;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime endCreationDate;

}
