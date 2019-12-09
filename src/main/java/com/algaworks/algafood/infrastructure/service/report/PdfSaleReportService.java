package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.service.SaleQuerySevice;
import com.algaworks.algafood.domain.service.SaleReportService;
import com.algaworks.algafood.infrastructure.service.report.exception.ReportException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfSaleReportService implements SaleReportService {

	@Autowired
	private SaleQuerySevice saleQueryService;
	
	@Override
	public byte[] issueDailySales(DailySaleFilter filter, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
			
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
					
			var dataSource = new JRBeanCollectionDataSource(saleQueryService.findDailySales(filter, timeOffset));
			
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch(Exception ex) {
			throw new ReportException("Não foi possível emitir o relatório de vendas diárias.", ex);
		}
	}
	
}
