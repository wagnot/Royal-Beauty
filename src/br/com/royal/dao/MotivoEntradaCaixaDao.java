package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.MotivoEntradaCaixa;

public class MotivoEntradaCaixaDao {
	public static void cadastrarMotivoEntradaCaixa(MotivoEntradaCaixa qp) {
		String query = "INSERT INTO tbMotivoEntradaCaixa (descricaoMotivoEntradaCaixa, statusMotivoEntradaCaixa)"
				+ " VALUES (?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, qp.getDescricao());
			pst.setInt(2, 1);
			pst.execute();

		} catch (Exception e) {
			System.out.println("CadastrarMotivoEntradaCaixa" + e.getMessage());
		}
	}

	public static void editarMotivoEntradaCaixa(MotivoEntradaCaixa f, int cod) {
		String query = "UPDATE tbMotivoEntradaCaixa set descricaoMotivoEntradaCaixa = ? WHERE codMotivoEntradaCaixa = ?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, f.getDescricao());
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("editarMotivoEntradaCaixa:\n"
						+ e.getMessage());
			}
		}
	}

	public static void excluirMotivoEntradaCaixa(int cod) {
		String query = "UPDATE tbMotivoEntradaCaixa set statusMotivoEntradaCaixa=? WHERE codMotivoEntradaCaixa=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);

				pst.setInt(1, 0);
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("excluirMotivoEntradaCaixa:\n"
						+ e.getMessage());
			}
		}

	}

	public static ArrayList<MotivoEntradaCaixa> getMotivoEntradaCaixas() {
		String query = "SELECT * from tbMotivoEntradaCaixa WHERE statusMotivoEntradaCaixa = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<MotivoEntradaCaixa> MotivoEntradaCaixas = new ArrayList<MotivoEntradaCaixa>();
			while (rs.next()) {
				MotivoEntradaCaixa f = new MotivoEntradaCaixa();
				f.setCod((rs.getInt("codMotivoEntradaCaixa")));
				f.setDescricao(rs
						.getString("descricaoMotivoEntradaCaixa"));
				MotivoEntradaCaixas.add(f);

			}
			return MotivoEntradaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<MotivoEntradaCaixa> getMotivoEntradaCaixas(String nome) {
		String query = "SELECT * from tbMotivoEntradaCaixa WHERE descricaoMotivoEntradaCaixa like ? AND statusMotivoEntradaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<MotivoEntradaCaixa> formasDePagamento = new ArrayList<MotivoEntradaCaixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				MotivoEntradaCaixa f = new MotivoEntradaCaixa();
				f.setCod((rs.getInt("codMotivoEntradaCaixa")));
				f.setDescricao(rs
						.getString("descricaoMotivoEntradaCaixa"));
				formasDePagamento.add(f);
			}
			return formasDePagamento;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String getDescricaoMotivoEntradaCaixa(String descricao, int cod) {
		String query = "SELECT descricaoMotivoEntradaCaixa FROM tbMotivoEntradaCaixa WHERE (descricaoMotivoEntradaCaixa) like ?"
				+ " AND statusMotivoEntradaCaixa=1 AND codMotivoEntradaCaixa!=?";
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
				getNome = rs.getString("descricaoMotivoEntradaCaixa");
			}
			return getNome;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade Parcela;\n"
					+ e.getMessage());
		}
		return null;
	}

	public static MotivoEntradaCaixa getMotivoEntradaCaixa(int cod) {
		String query = "SELECT * FROM tbMotivoEntradaCaixa WHERE codMotivoEntradaCaixa=? AND statusMotivoEntradaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		MotivoEntradaCaixa f = new MotivoEntradaCaixa();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCod(rs.getInt("codMotivoEntradaCaixa"));
				f.setDescricao(rs
						.getString("descricaoMotivoEntradaCaixa"));

			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getMotivoEntradaCaixa;\n"
					+ e.getMessage());
		}
		return null;
	}


}
