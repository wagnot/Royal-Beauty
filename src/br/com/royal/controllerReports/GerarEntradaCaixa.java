package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.royal.modelReports.RelatorioEntradaCaixa;
import br.com.royal.modelReports.RelatorioMotivoEntradaProduto;

public class GerarEntradaCaixa {
	public int gerarEntradaCaixa(ArrayList<RelatorioEntradaCaixa> motivosEntradaCaixa) {
		System.out.println("Gerando relat�rio...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioEntradaCaixa.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(motivosEntradaCaixa));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioEntradaCaixa.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioEntradaCaixa.pdf");
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
