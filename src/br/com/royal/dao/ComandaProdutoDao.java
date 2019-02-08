package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import br.com.royal.controller.ProdutoController;
import br.com.royal.model.ComandaProduto;
import br.com.royal.model.Horario;
import br.com.royal.model.Produto;

public class ComandaProdutoDao {

	public static Produto getProduto(String codigoBarras) {
		String query = "SELECT * FROM tbProduto  INNER JOIN tbFabricante on tbProduto.codFabricante = tbFabricante.codFabricante WHERE codigoDeBarras = ? "
				+ "AND atividadeProduto=1";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, codigoBarras);

			ResultSet rs = pst.executeQuery();
			Produto p = new Produto();
			while (rs.next()) {
				p.setId(rs.getInt("codProduto"));
				p.setNomeProduto(rs.getString("nomeProduto"));
				p.setDescricaoProduto(rs.getString("descricaoProduto"));
				p.setValor(rs.getDouble("valorProduto"));
				p.setQuantidade(rs.getInt("quantidadeMinima"));
				p.setFotoProduto(rs.getString("fotoProduto"));
				p.setCodFabricante(rs.getInt("codFabricante"));
				p.setCodBarras(rs.getString("codigoDeBarras"));
				p.setNomeFabricante(rs.getString("nomeFabricante"));
				p.setAtividadeProduto(rs.getInt("atividadeProduto"));

			}
			return p;
		} catch (Exception e) {
			System.out.println("Quebrou pra conferir" + e.getMessage());
			return null;
		}
	}
	
	public static ArrayList<Produto> getItensComanda(ComandaProduto cp){
		ArrayList<Produto> itens = new ArrayList<Produto>();
		String query = "SELECT * FROM tbItensComandaProduto WHERE codComandaProduto=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, cp.getCodComandaProduto());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("codProduto"));
				p = new ProdutoController().findById(p.getId());
			}
			return itens;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static int cadastrarComandaProduto(ComandaProduto comanda){
		String query = "INSERT INTO tbComandaProduto (valorTotalComandaProduto, dataComandaProduto)"
				+ "VALUES(?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, comanda.getSubTotalProduto());
			pst.setString(2, comanda.getDataComanda());
			
			pst.execute();
			return getCodMaxComandaProduto();
		} catch (Exception e) {
			System.out.println("erro cadastrarComandaProduto:\n" + e.getMessage());
		}
		return 0;
		
	}

	private static int getCodMaxComandaProduto(){
		String query = "SELECT codComandaProduto FROM tbComandaProduto WHERE codComandaProduto=(SELECT MAX(codComandaProduto) FROM tbComandaProduto)";
		Connection con = null;
		ResultSet rs = null;
		int cod=0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod=rs.getInt("codComandaProduto");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCodMaxComandaProduto;\n" + e.getMessage());
		}
		return 0;
	}
}
