package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.royal.model.ComandaProduto;
import br.com.royal.model.Produto;
import br.com.royal.model.VendaProduto;

public class ItensVendaProdutoDao {
	
	public void cadastrarItensVendaProduto(VendaProduto vp,ComandaProduto comanda){
		Connection con = null;
		String query = "INSERT INTO tbItensVendaProduto(codVendaProduto, codProduto, quantidadeItensVendaProduto, subTotalItensVenda) "
				+ "VALUES (?,?,?,?)";
		con = Conexao.getConexao();
		try {
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, vp.getCodVendaProduto());
			for (Produto p : comanda.getProdutos()) {
				pstm.setLong(2, p.getId());
				pstm.setInt(3, p.getQuantidadeProdutoComanda());
				pstm.setDouble(4,
						p.getValor() * p.getQuantidadeProdutoComanda());
				

				pstm.execute();
			}
		} catch (Exception ex) {
			System.err.println("Quebro na inserir itens venda produto/n:"
					+ ex.getMessage());
		}
	}
		
	}
	
	
	


