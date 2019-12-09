package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;

public interface SaleQuerySevice {
	
	List<DailySale> findDailySales(DailySaleFilter filter, String timeOffset);

}
