package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import br.com.royal.modelReports.RelatorioMotivoEntradaCaixa;
import br.com.royal.modelReports.RelatorioMotivoSaidaCaixa;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GerarMotivoSaidaCaixa {
	public int gerarMotivoSaidaCaixa(ArrayList<RelatorioMotivoSaidaCaixa> saidacaixa) {
		System.out.println("Gerando relatório...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioMotivoSaidaCaixa.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(saidacaixa));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioMotivoSaidaCaixa.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioMotivoSaidaCaixa.pdf");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Relatório gerado.");
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}

}
