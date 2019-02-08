package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import br.com.royal.modelReports.ReciboComanda;
import br.com.royal.modelReports.RelatorioMotivoEntradaCaixa;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GerarRelatorioMotivoEntradaCaixa {

	public int gerarMotivoEntradaCaixa(ArrayList<RelatorioMotivoEntradaCaixa> entradacaixa) {
		System.out.println("Gerando relat�rio...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioMotivoEntradaCaixa.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(entradacaixa));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioMotivoEntradaCaixa.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioMotivoEntradaCaixa.pdf");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Relat�rio gerado.");
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}
}
