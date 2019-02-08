package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.Compilable;

import br.com.royal.model.Produto;

public class ProdutoDao {
	public Produto conferirCodigoBarras(Produto codigoBarras) {
		String query = "SELECT * FROM tbProduto  INNER JOIN tbFabricante on tbProduto.codFabricante = tbFabricante.codFabricante WHERE codigoDeBarras = ? ";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, codigoBarras.getCodBarras());

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
				p.setCustoProduto(rs.getDouble("custoProduto"));
				p.setLucroProduto(rs.getDouble("lucroProduto"));
				// p.setCodFornecedor(rs.getInt("codFornecedor"));
				codigoBarras = p;
			}
			return p;
		} catch (Exception e) {
			System.out.println("Quebrou pra conferir" + e.getMessage());
			return null;
		}
	}

	public Produto conferirCodigoBarras(String codigoBarras) {
		String query = "SELECT * FROM tbProduto  INNER JOIN tbFabricante on tbProduto.codFabricante = tbFabricante.codFabricante WHERE codigoDeBarras = ? ";
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
				p.setCustoProduto(rs.getDouble("custoProduto"));
				p.setLucroProduto(rs.getDouble("lucroProduto"));
				// p.setCodFornecedor(rs.getInt("codFornecedor"));

			}
			return p;
		} catch (Exception e) {
			System.out.println("Quebrou pra conferir" + e.getMessage());
			return null;
		}
	}

	public boolean conferirBarras(String codigoBarras) {
		String query = "SELECT * FROM tbProduto WHERE codigoDeBarras =? and atividadeProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, codigoBarras);
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			while (!rs.next()) {
				return true;
			}

		} catch (Exception e) {

		}
		return false;
	}

	public boolean conferirNomeDescricao(Produto p) {
		String query = "SELECT * FROM tbProduto WHERE nomeProduto = ? and descricaoProduto = ? and atividadeProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, p.getNomeProduto());
			pst.setString(2, p.getDescricaoProduto());
			pst.setInt(3, 1);
			ResultSet rs = pst.executeQuery();
			while (!rs.next()) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("bugou no conferir" + e.getMessage());
		}
		return false;
	}

	public void inserirProduto(Produto p) {

		String query = "INSERT INTO tbProduto (nomeProduto,descricaoProduto,valorProduto,quantidadeMinima,fotoProduto,codFabricante,codigoDeBarras,atividadeProduto,custoProduto,lucroProduto)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);

			// pst.setLong(1, p.getId());
			pst.setString(1, p.getNomeProduto());
			pst.setString(2, p.getDescricaoProduto());
			pst.setDouble(3, p.getValor());
			pst.setInt(4, p.getQuantidade());
			pst.setString(5, p.getFotoProduto());
			pst.setInt(6, p.getCodFabricante());
			pst.setString(7, p.getCodBarras());
			pst.setInt(8, 1);
			pst.setDouble(9,p.getCustoProduto());
			pst.setDouble(10,p.getLucroProduto());
			

			pst.execute();

		} catch (Exception e) {
			System.out.println("oeaea mashgauysgya" + e.getMessage());
		}

	}

	// CORRIGIR ESSE MÉTODO
	public boolean excluirProduto(Produto p) {
		String query = "UPDATE tbProduto set atividadeProduto=? where nomeProduto = ? and descricaoProduto=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 0);
			pst.setString(2, p.getNomeProduto());
			pst.setString(3, p.getDescricaoProduto());
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean editar(Produto p, String a, String b) {
		String query = "UPDATE tbProduto set nomeProduto=? ,descricaoProduto = ?,valorProduto = ?,quantidadeMinima = ?,"
				+ "fotoProduto = ?,codFabricante = ?, codigoDeBarras = ?,custoProduto = ?,lucroProduto = ? where nomeProduto=? and descricaoProduto=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, p.getNomeProduto());
			pst.setString(2, p.getDescricaoProduto());
			pst.setDouble(3, p.getValor());
			pst.setInt(4, p.getQuantidade());
			pst.setString(5, p.getFotoProduto());
			pst.setInt(6, p.getCodFabricante());
			pst.setString(7, p.getCodBarras());
			pst.setDouble(8, p.getCustoProduto());
			pst.setDouble(9, p.getLucroProduto());
			

			pst.setString(10, a);
			pst.setString(11, b);

			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	// CORRIGIR
	public static List<Produto> listar() {
		String query = "SELECT * from tbProduto INNER JOIN tbFabricante on tbProduto.codFabricante = tbFabricante.codFabricante WHERE atividadeProduto = ?";
		// String sql =
		// "SELECT nomeFabricante FROM tbFabricante WHERE codFabricante = ? ";
		// String sq1 =
		// "SELECT nomeFornecedor FROM tbFornecedor WHERE codFornecedor = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			List<Produto> produtos = new ArrayList<Produto>();
			while (rs.next()) {

				Produto p = new Produto();
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
				p.setCustoProduto(rs.getDouble("custoProduto"));
				p.setLucroProduto(rs.getDouble("lucroProduto"));
				// p.setCodFornecedor(rs.getInt("codFornecedor"));
				// p.setCodEntradaProduto(rs.getInt("codEntradaProduto"));

				// try {

				// PreparedStatement pstm = con.prepareStatement(sql);
				// pstm.setInt(1, p.getCodFabricante());
				// ResultSet rs1 = pstm.executeQuery();
				// while (rs1.next()) {
				// p.setNomeFabricante(rs1.getString("nomeFabricante"));
				// }
				// } catch (Exception e) {
				// System.out.println("Quebrou" + e.getMessage());
				// }

				produtos.add(p);
			}
			return produtos;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public static List<Produto> find(String nomeProduto) {
		String query = "SELECT * from tbProduto INNER JOIN tbFabricante on tbProduto.codFabricante = tbFabricante.codFabricante WHERE atividadeProduto =? and  nomeProduto LIKE ?   ";
		// String sql =
		// "SELECT nomeFabricante FROM tbFabricante WHERE codFabricante = ? ";
		// String sq1 =
		// "SELECT nomeFornecedor FROM tbFornecedor WHERE codFornecedor = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			pst.setString(2, "%"+ nomeProduto+"%" );
			
			
			ResultSet rs = pst.executeQuery();
			List<Produto> produtos = new ArrayList<Produto>();
			while (rs.next()) {

				Produto p = new Produto();
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
				p.setCustoProduto(rs.getDouble("custoProduto"));
				p.setLucroProduto(rs.getDouble("lucroProduto"));
				// p.setCodFornecedor(rs.getInt("codFornecedor"));
				// p.setCodEntradaProduto(rs.getInt("codEntradaProduto"));

				// try {

				// PreparedStatement pstm = con.prepareStatement(sql);
				// pstm.setInt(1, p.getCodFabricante());
				// ResultSet rs1 = pstm.executeQuery();
				// while (rs1.next()) {

				// }
				// } catch (Exception e) {
				// System.out.println("Quebrou" + e.getMessage());
				// }
				
				produtos.add(p);
			}
			return produtos;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	
	public static List<Produto> findBarras(String codBarras) {
		String query = "SELECT * from tbProduto INNER JOIN tbFabricante on tbProduto.codFabricante = tbFabricante.codFabricante WHERE atividadeProduto =? and  codigoDeBarras LIKE ?   ";
		// String sql =
		// "SELECT nomeFabricante FROM tbFabricante WHERE codFabricante = ? ";
		// String sq1 =
		// "SELECT nomeFornecedor FROM tbFornecedor WHERE codFornecedor = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			pst.setString(2, "%"+ codBarras+"%" );
			
			
			ResultSet rs = pst.executeQuery();
			List<Produto> produtos = new ArrayList<Produto>();
			while (rs.next()) {

				Produto p = new Produto();
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
				p.setCustoProduto(rs.getDouble("custoProduto"));
				p.setLucroProduto(rs.getDouble("lucroProduto"));
				// p.setCodFornecedor(rs.getInt("codFornecedor"));
				// p.setCodEntradaProduto(rs.getInt("codEntradaProduto"));

				// try {

				// PreparedStatement pstm = con.prepareStatement(sql);
				// pstm.setInt(1, p.getCodFabricante());
				// ResultSet rs1 = pstm.executeQuery();
				// while (rs1.next()) {

				// }
				// } catch (Exception e) {
				// System.out.println("Quebrou" + e.getMessage());
				// }
				
				produtos.add(p);
			}
			return produtos;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Produto findById(long codProduto) {
		String query = "SELECT * from tbProduto where codProduto=? and atividadeProduto = 1";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1, codProduto);
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			Produto produto = null;
			while (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("codProduto"));
				p.setNomeProduto(rs.getString("nomeProduto"));
				p.setDescricaoProduto(rs.getString("descricaoProduto"));
				p.setValor(rs.getDouble("valorProduto"));
				p.setQuantidade(rs.getInt("quantidadeProduto"));
				p.setFotoProduto(rs.getString("fotoProduto"));
				p.setCodFabricante(rs.getInt("codFabricante"));
				p.setCodBarras(rs.getString("codigoDeBarras"));
				p.setAtividadeProduto(rs.getInt("atividadeProduto"));
				p.setCustoProduto(rs.getDouble("custoProduto"));
				p.setLucroProduto(rs.getDouble("lucroProduto"));
				// p.setCodFornecedor(rs.getInt("codFornecedor"));

				produto = p;
			}
			return produto;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
