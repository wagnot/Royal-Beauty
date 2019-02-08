package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import br.com.royal.modelReports.RelatorioFabricante;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GerarRelatorioFabricante {
	public int gerarServicos(ArrayList<RelatorioFabricante> fabricantes) {
		System.out.println("Gerando relatório...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioFabricante.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(fabricantes));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioFabricante.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioFabricante.pdf");
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
