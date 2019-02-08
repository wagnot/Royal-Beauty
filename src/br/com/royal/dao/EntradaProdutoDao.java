package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.royal.model.EntradaProduto;
import br.com.royal.model.Produto;

public class EntradaProdutoDao {

	public void inserir(EntradaProduto ep) {
		String query = "INSERT INTO tbEntradaProduto (quantidadeEntradaProduto,dataEntradaProduto,"
				+ "codMotivoEntradaProduto,loteProduto,dataValidadeLote,codFornecedor,codProduto,atividadeEntrada)"
				+ "VALUES(?,?,?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);

			pst.setInt(1, ep.getQuantidadeEntradaProduto());
			pst.setString(2, ep.getDataEntradaProduto());
			pst.setInt(3, ep.getCodMotivoEntradaProduto());
			pst.setString(4, ep.getLoteProduto());
			pst.setString(5, ep.getDataValidadeLote());
			pst.setInt(6, ep.getCodFornecedor());
			pst.setLong(7, ep.getCodProduto());
			pst.setInt(8, 1);
			pst.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean excluirEntradaProduto(int codigo) {
		String query = "UPDATE  tbEntradaProduto set atividadeEntrada = ? where codEntradaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 0);
			pst.setInt(2, codigo);
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean editar(EntradaProduto ep, int codigo) {
		String query = "UPDATE tbEntradaProduto set quantidadeEntradaProduto=? , dataEntradaProduto = ?,"
				+ " codMotivoEntradaProduto = ?,loteProduto = ?,dataValidadeLote = ?, codFornecedor = ?, codProduto = ? WHERE codEntradaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, ep.getQuantidadeEntradaProduto());
			pst.setString(2, ep.getDataEntradaProduto());
			pst.setInt(3, ep.getCodMotivoEntradaProduto());
			pst.setString(4, ep.getLoteProduto());
			pst.setString(5, ep.getDataValidadeLote());
			pst.setInt(6, ep.getCodFornecedor());
			pst.setLong(7, ep.getCodProduto());
			pst.setLong(8, codigo);
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public static List<EntradaProduto> listar() {
		String query = "SELECT * from tbEntradaProduto INNER JOIN tbProduto on tbEntradaProduto.codProduto = tbProduto.codProduto "
				+ "INNER JOIN tbFornecedor on tbEntradaProduto.codFornecedor = tbFornecedor.codFornecedor "
				+ "INNER JOIN tbMotivoEntradaProduto on tbEntradaProduto.codMotivoEntradaProduto = tbMotivoEntradaProduto.codMotivoEntradaProduto WHERE atividadeEntrada = ? ";
		// String sql =
		// "SELECT descricaoEntradaProduto FROM tbMotivoEntradaProduto WHERE codMotivoEntradaProduto = ? ";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			List<EntradaProduto> entrada = new ArrayList<EntradaProduto>();
			while (rs.next()) {

				EntradaProduto ep = new EntradaProduto();
				ep.setIdEntradaProduto((rs.getInt("codEntradaProduto")));
				ep.setQuantidadeEntradaProduto(rs
						.getInt("quantidadeEntradaProduto"));
				ep.setDataEntradaProduto(rs.getString("dataEntradaProduto"));
				ep.setCodMotivoEntradaProduto(rs
						.getInt("codMotivoEntradaProduto"));
				ep.setLoteProduto(rs.getString("loteProduto"));
				ep.setDataValidadeLote(rs.getString("dataValidadeLote"));
				ep.setNomeProduto(rs.getString("codigoDeBarras"));
				ep.setNomeFornecedor(rs.getString("nomeFornecedor"));
				ep.setMotivo(rs.getString("descricaoEntradaProduto"));
				

				entrada.add(ep);
			}
			return entrada;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public EntradaProduto findById(int codEntradaProduto) {
		String query = "SELECT * from tbEntradaProduto where codEntradaProduto=? and atividadeEntrada = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1, codEntradaProduto);
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			EntradaProduto entrada = new EntradaProduto();
			while (rs.next()) {
				
				entrada.setIdEntradaProduto((rs.getInt("codEntradaProduto")));
				entrada.setQuantidadeEntradaProduto((rs
						.getInt("quantidadeEntradaProduto")));
				entrada.setDataEntradaProduto(rs.getString("dataEntradaProduto"));
				entrada.setCodMotivoEntradaProduto((rs
						.getInt("codMotivoEntradaProduto")));
				entrada.setLoteProduto(rs.getString("loteProduto"));
				entrada.setDataValidadeLote(rs.getString("dataValidadeLote"));
				entrada.setCodProduto(rs.getLong("codProduto"));

				
			}
			return entrada;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static List<EntradaProduto> find(String codigoBarras) {
		String query = "SELECT * from tbEntradaProduto INNER JOIN tbProduto on tbEntradaProduto.codProduto = tbProduto.codProduto "
				+ "INNER JOIN tbFornecedor on tbEntradaProduto.codFornecedor = tbFornecedor.codFornecedor "
				+ "INNER JOIN tbMotivoEntradaProduto on tbEntradaProduto.codMotivoEntradaProduto = tbMotivoEntradaProduto.codMotivoEntradaProduto WHERE codigoDeBarras LIKE ? and atividadeEntrada = ?";
		// String sql =
		// "SELECT descricaoEntradaProduto FROM tbMotivoEntradaProduto WHERE codMotivoEntradaProduto = ? ";
		// String sql1 =
		// "SELECT nomeProduto FROM tbProduto WHERE codProduto = ? ";
		// String sql2 =
		// "SELECT nomeFornecedor FROM tbFornecedor WHERE codFornecedor = ? ";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%" + codigoBarras + "%");
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			List<EntradaProduto> entrada = new ArrayList<EntradaProduto>();
			while (rs.next()) {

				EntradaProduto ep = new EntradaProduto();
				ep.setIdEntradaProduto((rs.getInt("codEntradaProduto")));
				ep.setQuantidadeEntradaProduto(rs
						.getInt("quantidadeEntradaProduto"));
				ep.setDataEntradaProduto(rs.getString("dataEntradaProduto"));
				ep.setCodMotivoEntradaProduto(rs
						.getInt("codMotivoEntradaProduto"));
				ep.setLoteProduto(rs.getString("loteProduto"));
				ep.setDataValidadeLote(rs.getString("dataValidadeLote"));
				ep.setNomeProduto(rs.getString("codigoDeBarras"));
				ep.setNomeFornecedor(rs.getString("nomeFornecedor"));
				ep.setMotivo(rs.getString("descricaoEntradaProduto"));

				entrada.add(ep);
			}
			return entrada;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

}
