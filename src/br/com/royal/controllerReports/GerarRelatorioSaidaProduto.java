package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.royal.modelReports.RelatorioProdutos;
import br.com.royal.modelReports.RelatorioSaidaProduto;

public class GerarRelatorioSaidaProduto {
	public int gerarSaidaProduto(ArrayList<RelatorioSaidaProduto> saidaprodutos) {
		System.out.println("Gerando relatório...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioSaidaProduto.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(saidaprodutos));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioSaidaProduto.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioSaidaProduto.pdf");
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
