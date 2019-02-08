package br.com.royal.controllerReports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import br.com.royal.dao.Conexao;
import br.com.royal.modelReports.RelatorioComandapServico;


public class GerarRelatorioComandapServico {
	public static RelatorioComandapServico gerarRelaorio(){
		RelatorioComandapServico nois = new RelatorioComandapServico();
		Connection conn = null;
		
			try {
				conn = Conexao.getConexao();
				PreparedStatement pstm = conn.prepareCall("{ call uspGerarRelatorioComandapServico()}");
				ResultSet rs = pstm.executeQuery();
				JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
				
				if(rs.next()){
					nois.setNomeServico(rs.getString("nomeServico"));
					nois.setValorServico(rs.getFloat("valorProduto"));
					nois.setDataComandaServico(rs.getTimestamp("dataComandaServico")+"");
					nois.setValorTotalVendaServico(rs.getDouble("valorTotalVendaServico"));
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			} return nois;
	}
}
