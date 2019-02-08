package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.royal.model.ComandaServico;
import br.com.royal.model.Servico;
import br.com.royal.model.VendaServico;

public class ItensVendaServicoDao {
	public void cadastrarItensVendaServico(VendaServico vs,
			ComandaServico comanda) {
		Connection con = null;
		String query = "INSERT INTO tbItensVendaServico(codVendaServico, codServico, precoServico)"
				+ "VALUES (?,?,?)";
		con = Conexao.getConexao();
		try {
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, vs.getCodVendaServico());
			for (Servico s : comanda.getServicos()) {
				pstm.setInt(2, s.getCodServico());
				pstm.setDouble(3, s.getValorServico());

				pstm.execute();
			}
		} catch (Exception ex) {
			System.err.println("Quebro na inserir itens venda servico/n:"
					+ ex.getMessage());
		}
	}
}
