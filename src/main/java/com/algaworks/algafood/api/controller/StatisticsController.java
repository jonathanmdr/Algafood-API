package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQuerySevice;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
	
	@Autowired
	private SaleQuerySevice saleQuerySevice;
	
	@GetMapping("daily-sales")
	public List<DailySale> findDailySales(DailySaleFilter filter) {
		return saleQuerySevice.findDailySales(filter);
	}

}
