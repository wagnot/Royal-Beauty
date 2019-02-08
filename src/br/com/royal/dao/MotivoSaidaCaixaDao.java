package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.MotivoSaidaCaixa;

public class MotivoSaidaCaixaDao {
	public static void cadastrarMotivoSaidaCaixa(MotivoSaidaCaixa qp) {
		String query = "INSERT INTO tbMotivoSaidaCaixa (descricaoMotivoSaidaCaixa, statusMotivoSaidaCaixa)"
				+ " VALUES (?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, qp.getDescricao());
			pst.setInt(2, 1);
			pst.execute();

		} catch (Exception e) {
			System.out.println("CadastrarMotivoSaidaCaixa" + e.getMessage());
		}
	}

	public static void editarMotivoSaidaCaixa(MotivoSaidaCaixa f, int cod) {
		String query = "UPDATE tbMotivoSaidaCaixa set descricaoMotivoSaidaCaixa = ? WHERE codMotivoSaidaCaixa = ?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, f.getDescricao());
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("editarMotivoSaidaCaixa:\n"
						+ e.getMessage());
			}
		}
	}

	public static void excluirMotivoSaidaCaixa(int cod) {
		String query = "UPDATE tbMotivoSaidaCaixa set statusMotivoSaidaCaixa=? WHERE codMotivoSaidaCaixa=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);

				pst.setInt(1, 0);
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("excluirMotivoSaidaCaixa:\n"
						+ e.getMessage());
			}
		}

	}

	public static ArrayList<MotivoSaidaCaixa> getMotivoSaidaCaixas() {
		String query = "SELECT * from tbMotivoSaidaCaixa WHERE statusMotivoSaidaCaixa = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<MotivoSaidaCaixa> MotivoSaidaCaixas = new ArrayList<MotivoSaidaCaixa>();
			while (rs.next()) {
				MotivoSaidaCaixa f = new MotivoSaidaCaixa();
				f.setCod((rs.getInt("codMotivoSaidaCaixa")));
				f.setDescricao(rs.getString("descricaoMotivoSaidaCaixa"));
				MotivoSaidaCaixas.add(f);

			}
			return MotivoSaidaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<MotivoSaidaCaixa> getMotivoSaidaCaixas(
			String nome) {
		String query = "SELECT * from tbMotivoSaidaCaixa WHERE descricaoMotivoSaidaCaixa like ? AND statusMotivoSaidaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<MotivoSaidaCaixa> formasDePagamento = new ArrayList<MotivoSaidaCaixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				MotivoSaidaCaixa f = new MotivoSaidaCaixa();
				f.setCod((rs.getInt("codMotivoSaidaCaixa")));
				f.setDescricao(rs.getString("descricaoMotivoSaidaCaixa"));
				formasDePagamento.add(f);
			}
			return formasDePagamento;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String getDescricaoMotivoSaidaCaixa(String descricao,
			int cod) {
		String query = "SELECT descricaoMotivoSaidaCaixa FROM tbMotivoSaidaCaixa WHERE (descricaoMotivoSaidaCaixa) like ?"
				+ " AND statusMotivoSaidaCaixa=1 AND codMotivoSaidaCaixa!=?";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getNome = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + descricao + "%");
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getNome = rs.getString("descricaoMotivoSaidaCaixa");
			}
			return getNome;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade Parcela;\n"
					+ e.getMessage());
		}
		return null;
	}

	public static MotivoSaidaCaixa getMotivoSaidaCaixa(int cod) {
		String query = "SELECT * FROM tbMotivoSaidaCaixa WHERE codMotivoSaidaCaixa=? AND statusMotivoSaidaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		MotivoSaidaCaixa f = new MotivoSaidaCaixa();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCod(rs.getInt("codMotivoSaidaCaixa"));
				f.setDescricao(rs.getString("descricaoMotivoSaidaCaixa"));

			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getMotivoSaidaCaixa;\n"
					+ e.getMessage());
		}
		return null;
	}

}
