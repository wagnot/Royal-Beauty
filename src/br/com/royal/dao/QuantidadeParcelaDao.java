package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.QuantidadeParcela;

public class QuantidadeParcelaDao {
	
	public static void cadastrarQuantidadeParcela(QuantidadeParcela qp) {
		String query = "INSERT INTO tbQuantidadeParcela (descricaoQuantidadeParcela, statusQuantidadeParcela)"
				+ " VALUES (?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, qp.getDescricaoParcela());
			pst.setInt(2, 1);
			pst.execute();

		} catch (Exception e) {
			System.out.println("CadastrarParcela" + e.getMessage());
		}
	}

	public static void editarQuantidadeParcela(QuantidadeParcela f, int cod) {
		String query = "UPDATE tbQuantidadeParcela set descricaoQuantidadeParcela = ? WHERE codQuantidadeParcela = ?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, f.getDescricaoParcela());
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("editarParcela :\n"
						+ e.getMessage());
			}
		}
	}

	public static void excluirQuantidadeParcela(int cod) {
		String query = "UPDATE tbQuantidadeParcela set statusQuantidadeParcela=? WHERE codQuantidadeParcela=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);

				pst.setInt(1, 0);
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("excluirParcela:\n"
						+ e.getMessage());
			}
		}

	}

	public static ArrayList<QuantidadeParcela> getQuantidadeParcelas() {
		String query = "SELECT * from tbQuantidadeParcela WHERE statusQuantidadeParcela = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<QuantidadeParcela> quantidadeParcelas = new ArrayList<QuantidadeParcela>();
			while (rs.next()) {
				QuantidadeParcela f = new QuantidadeParcela();
				f.setCodParcela((rs.getInt("codQuantidadeParcela")));
				f.setDescricaoParcela(rs
						.getString("descricaoQuantidadeParcela"));
				quantidadeParcelas.add(f);

			}
			return quantidadeParcelas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<QuantidadeParcela> getQuantidadeParcelas(String nome) {
		String query = "SELECT * from tbQuantidadeParcela WHERE descricaoQuantidadeParcela like ? AND statusQuantidadeParcela = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<QuantidadeParcela> formasDePagamento = new ArrayList<QuantidadeParcela>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				QuantidadeParcela f = new QuantidadeParcela();
				f.setCodParcela((rs.getInt("codQuantidadeParcela")));
				f.setDescricaoParcela(rs
						.getString("descricaoQuantidadeParcela"));
				formasDePagamento.add(f);
			}
			return formasDePagamento;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String getDescricaoQuantidadeParcela(String descricao, int cod) {
		String query = "SELECT descricaoQuantidadeParcela FROM tbQuantidadeParcela WHERE (descricaoQuantidadeParcela) like ?"
				+ " AND statusQuantidadeParcela=1 AND codQuantidadeParcela!=?";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getNome = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + descricao+ "%");
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getNome = rs.getString("descricaoQuantidadeParcela");
			}
			return getNome;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade Parcela;\n"
					+ e.getMessage());
		}
		return null;
	}

	public static QuantidadeParcela getQuantidadeParcela(int cod) {
		String query = "SELECT * FROM tbQuantidadeParcela WHERE codQuantidadeParcela=? AND statusQuantidadeParcela = ?";
		Connection con = null;
		ResultSet rs = null;
		QuantidadeParcela f = new QuantidadeParcela();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCodParcela(rs.getInt("codQuantidadeParcela"));
				f.setDescricaoParcela(rs
						.getString("descricaoQuantidadeParcela"));

			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getQuantidadeParcela;\n"
					+ e.getMessage());
		}
		return null;
	}
}
