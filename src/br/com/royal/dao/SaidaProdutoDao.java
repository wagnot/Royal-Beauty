package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.royal.model.EntradaProduto;
import br.com.royal.model.SaidaProduto;

public class SaidaProdutoDao {

	public void inserir(SaidaProduto sp) {
		String query = "INSERT INTO tbSaidaProduto (codMotivoSaidaProduto,dataSaidaProduto,quantidadeSaidaProduto,codProduto)"
				+ "VALUES(?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);

			pst.setInt(1, sp.getCodMotivoSaidaProduto());
			pst.setString(2, sp.getDataSaidaProduto());
			pst.setInt(3, sp.getQuantidadeSaidaProduto());
			pst.setLong(4, sp.getCodProduto());

			pst.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean editar(SaidaProduto sp, int codigo) {
		String query = "UPDATE tbSaidaProduto set codMotivoSaidaProduto=? , dataSaidaProduto = ?, quantidadeSaidaProduto = ?, codProduto = ? WHERE codSaidaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, sp.getCodMotivoSaidaProduto());
			pst.setString(2, sp.getDataSaidaProduto());
			pst.setInt(3, sp.getQuantidadeSaidaProduto());
			pst.setLong(4, sp.getCodProduto());
			pst.setLong(5, codigo);
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public static List<SaidaProduto> listar() {
		String query = "SELECT * from tbSaidaProduto INNER JOIN tbProduto on tbSaidaProduto.codProduto = tbProduto.codProduto "
				+ "INNER JOIN tbMotivoSaidaProduto on tbSaidaProduto.codMotivoSaidaProduto = tbMotivoSaidaProduto.codMotivoSaidaProduto ";
		// String sql =
		// "SELECT descricaoEntradaProduto FROM tbMotivoEntradaProduto WHERE codMotivoEntradaProduto = ? ";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			List<SaidaProduto> saida = new ArrayList<SaidaProduto>();
			while (rs.next()) {

				SaidaProduto sp = new SaidaProduto();
				sp.setCodSaidaProduto((rs.getInt("codSaidaProduto")));
				sp.setQuantidadeSaidaProduto(rs
						.getInt("quantidadeSaidaProduto"));
				sp.setDataSaidaProduto(rs.getString("dataSaidaProduto"));
				sp.setCodMotivoSaidaProduto(rs.getInt("codMotivoSaidaProduto"));
				sp.setNomeProduto(rs.getString("codigoDeBarras"));
				sp.setMotivo(rs.getString("descricaoMotivoSaidaProduto"));

				saida.add(sp);
			}
			return saida;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public static List<SaidaProduto> find(String dataSaidaProduto) {
		String query = "SELECT * from tbSaidaProduto INNER JOIN tbProduto on tbSaidaProduto.codProduto = tbProduto.codProduto "
				+ "INNER JOIN tbMotivoSaidaProduto on tbSaidaProduto.codMotivoSaidaProduto = tbMotivoSaidaProduto.codMotivoSaidaProduto WHERE codigoDeBarras LIKE ?  ";
		// String sql =
		// "SELECT descricaoEntradaProduto FROM tbMotivoEntradaProduto WHERE codMotivoEntradaProduto = ? ";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%"+dataSaidaProduto+"%");
			ResultSet rs = pst.executeQuery();
			List<SaidaProduto> saida = new ArrayList<SaidaProduto>();
			while (rs.next()) {
				pst.setString(1, dataSaidaProduto);
				SaidaProduto sp = new SaidaProduto();
				sp.setCodSaidaProduto((rs.getInt("codSaidaProduto")));
				sp.setQuantidadeSaidaProduto(rs
						.getInt("quantidadeSaidaProduto"));
				sp.setDataSaidaProduto(rs.getString("dataSaidaProduto"));
				sp.setCodMotivoSaidaProduto(rs.getInt("codMotivoSaidaProduto"));
				sp.setNomeProduto(rs.getString("codigoDeBarras"));
				sp.setMotivo(rs.getString("descricaoMotivoSaidaProduto"));

				saida.add(sp);
			}
			return saida;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public SaidaProduto findById(int codSaidaProduto) {
		String query = "SELECT * from tbSaidaProduto where codSaidaProduto=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1, codSaidaProduto);

			ResultSet rs = pst.executeQuery();
			SaidaProduto saida = new SaidaProduto();
			while (rs.next()) {

				saida.setCodSaidaProduto((rs.getInt("codSaidaProduto")));
				saida.setQuantidadeSaidaProduto((rs
						.getInt("quantidadeSaidaProduto")));
				saida.setDataSaidaProduto(rs.getString("dataSaidaProduto"));
				saida.setCodMotivoSaidaProduto((rs
						.getInt("codMotivoSaidaProduto")));
				saida.setCodProduto(rs.getLong("codProduto"));

			}
			return saida;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
