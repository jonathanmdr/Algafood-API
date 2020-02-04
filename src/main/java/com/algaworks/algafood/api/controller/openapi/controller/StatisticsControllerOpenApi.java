package com.algaworks.algafood.api.controller.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatísticas")
public interface StatisticsControllerOpenApi {
	
	@ApiOperation("Consulta estatísticas de vendas diárias")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "restaurantId", value = "Identificador do restaurante", example = "1", dataType = "int"),
		@ApiImplicitParam(name = "startCreationDate", value = "Data/hora inicial da criação do pedido",	example = "2019-12-01T00:00:00Z", dataType = "date-time"),
		@ApiImplicitParam(name = "endCreationDate", value = "Data/hora final da criação do pedido",	example = "2019-12-02T23:59:59Z", dataType = "date-time")
	})
	public List<DailySale> findDailySales(DailySaleFilter filter, 
                                          @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", defaultValue = "+00:00") String timeOffset);
	
	public ResponseEntity<byte[]> findDailySalesPdf(DailySaleFilter filter, String timeOffset);

}
