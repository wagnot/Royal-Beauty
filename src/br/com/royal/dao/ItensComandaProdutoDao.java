package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import br.com.royal.model.ComandaProduto;
import br.com.royal.model.Horario;
import br.com.royal.model.Produto;

public class ItensComandaProdutoDao {
	public void cadastrarItensComandaProduto(ComandaProduto comanda) {
		Connection con = null;
		String query = "INSERT INTO tbItensComandaProduto(codComandaProduto, codProduto, subTotalComandaProduto, quantidadeItensComandaProduto) "
				+ "VALUES (?,?,?,?)";
		con = Conexao.getConexao();
		try {
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, comanda.getCodComandaProduto());
			for (Produto p : comanda.getProdutos()) {
				pstm.setLong(2, p.getId());
				pstm.setDouble(3,
						p.getValor() * p.getQuantidadeProdutoComanda());
				pstm.setInt(4, p.getQuantidadeProdutoComanda());

				pstm.execute();
			}
		} catch (Exception ex) {
			System.err.println("Quebro na inserir itensComandaProduto/n:"
					+ ex.getMessage());
		}
	}
	
	
}
