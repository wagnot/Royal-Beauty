package br.com.royal.controllerReports;

import java.io.File;
import java.util.ArrayList;

import br.com.royal.modelReports.ReciboComanda;
import br.com.royal.modelReports.RelatorioFuncionarios;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GerarComanda {
	
	public int gerarComanda(ArrayList<ReciboComanda> comanda) {
		System.out.println("Gerando comanda...");
		try {
			JasperReport report = JasperCompileManager.compileReport("relatorios/Comanda.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(comanda));
			JasperExportManager.exportReportToPdfFile(print, "relatorios/Comanda.pdf");
			try {
				String str = new File("").getAbsolutePath();
				Runtime.getRuntime().exec("explorer " + str + "\\relatorios\\Comanda.pdf");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Comanda gerada.");
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}

}
