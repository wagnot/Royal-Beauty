package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.FormaDePagamento;

public class FormaDePagamentoDao {
	public static void cadastrarFormaDePagamento(FormaDePagamento f) {
		String query = "INSERT INTO tbformadepagamento (descricaoFormaDePagamento, statusFormaDePagamento)"
				+ " VALUES (?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, f.getDescricaFormaDePagamento());
			pst.setInt(2, 1);
			pst.execute();

		} catch (Exception e) {
			System.out.println("CadastrarFormaDePagamento" + e.getMessage());
		}
	}

	public static void editarFormaDePagamento(FormaDePagamento f, int cod) {
		String query = "UPDATE tbFormaDePagamento set descricaoFormaDePagamento = ? WHERE codFormaDePagamento = ?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, f.getDescricaFormaDePagamento());
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("editarFormaDePagamento :\n"
						+ e.getMessage());
			}
		}
	}

	public static void excluirFormaDePagamento(int cod) {
		String query = "UPDATE tbFormaDePagamento set statusFormaDePagamento=? WHERE codFormaDePagamento=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);

				pst.setInt(1, 0);
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("excluirFormaDePagamento:\n"
						+ e.getMessage());
			}
		}

	}

	public static ArrayList<FormaDePagamento> getFormasDePagamento() {
		String query = "SELECT * from tbFormaDePagamento WHERE statusFormaDePagamento = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<FormaDePagamento> formasDePagamento = new ArrayList<FormaDePagamento>();
			while (rs.next()) {
				FormaDePagamento f = new FormaDePagamento();
				f.setCodFormaDePagamento((rs.getInt("codFormaDePagamento")));
				f.setDescricaoFormaDePagamento(rs
						.getString("descricaoFormaDePagamento"));
				formasDePagamento.add(f);

			}
			return formasDePagamento;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<FormaDePagamento> getFormasDePagamento(String nome) {
		String query = "SELECT * from tbFormaDePagamento WHERE descricaoFormaDePagamento like ? AND statusFormaDePagamento = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<FormaDePagamento> formasDePagamento = new ArrayList<FormaDePagamento>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FormaDePagamento f = new FormaDePagamento();
				f.setCodFormaDePagamento((rs.getInt("codFormaDePagamento")));
				f.setDescricaoFormaDePagamento(rs
						.getString("descricaoFormaDePagamento"));
				formasDePagamento.add(f);
			}
			return formasDePagamento;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String getDescricaoFormaDePagamento(String descricao, int cod) {
		String query = "SELECT descricaoFormaDePagamento FROM tbFormaDePagamento WHERE (descricaoFormaDePagamento) like ?"
				+ " AND statusFormaDePagamento=1 AND codFormaDePagamento!=?";
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
				getNome = rs.getString("descricaoFormaDePagamento");
			}
			return getNome;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade FormaDePagamento;\n"
					+ e.getMessage());
		}
		return null;
	}

	public static FormaDePagamento getFormaDePagamento(int cod) {
		String query = "SELECT * FROM tbFormaDePagamento WHERE codFormaDePagamento=? AND statusFormaDePagamento = ?";
		Connection con = null;
		ResultSet rs = null;
		FormaDePagamento f = new FormaDePagamento();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCodFormaDePagamento(rs.getInt("codFormaDePagamento"));
				f.setDescricaoFormaDePagamento(rs
						.getString("descricaoFormaDePagamento"));

			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getFormaDePagamento;\n"
					+ e.getMessage());
		}
		return null;
	}
}
