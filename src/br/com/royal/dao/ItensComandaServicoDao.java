package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import br.com.royal.model.ComandaServico;
import br.com.royal.model.Servico;

public class ItensComandaServicoDao {
	public void cadastrarItensComandaServico(ComandaServico comanda) {
		Connection con = null;
		String query = "INSERT INTO tbItensComandaServico(codComandaServico, codServico, precoServico) "
				+ "VALUES (?,?,?)";
		con = Conexao.getConexao();
		try {
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, comanda.getComandaServico());
			for (Servico p : comanda.getServicos()) {
				pstm.setInt(2, p.getCodServico());
				pstm.setDouble(3, p.getValorServico());

				pstm.execute();
			}
		} catch (Exception ex) {
			System.err.println("Quebro na inserir itensComandaServico/n:"
					+ ex.getMessage());
		}
	}

}
