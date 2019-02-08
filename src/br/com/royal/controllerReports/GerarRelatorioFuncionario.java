package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import br.com.royal.modelReports.RelatorioFuncionarios;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GerarRelatorioFuncionario {
	public int gerarFuncionarioEspecifico(ArrayList<RelatorioFuncionarios> funcionarios) {
		System.out.println("Gerando relatório...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioFuncionarioEspecifico.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(funcionarios));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioFuncionarioEspecifico.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioFuncionarioEspecifico.pdf");
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
	
	public int gerarFuncionarioGeral(ArrayList<RelatorioFuncionarios> funcionarios) {
		System.out.println("Gerando relatório...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioFuncionarioGeral.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(funcionarios));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioFuncionarioGeral.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioFuncionarioGeral.pdf");
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
