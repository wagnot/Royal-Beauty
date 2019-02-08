package br.com.royal.controllerReports;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import br.com.royal.modelReports.RelatorioCaixa;
import br.com.royal.modelReports.RelatorioClientes;
import br.com.royal.modelReports.RelatorioMotivoSaidaProduto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class GerarRelatorioCaixa {
	public int gerarCaixa(ArrayList<RelatorioCaixa> caixa) {
		System.out.println("Gerando relatório...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/RelatorioCaixaEspecifico.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(caixa));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/RelatorioCaixaEspecifico.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\RelatorioCaixaEspecifico.pdf");
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
