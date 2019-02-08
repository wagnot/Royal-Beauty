package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import br.com.royal.model.Funcionario;
import br.com.royal.model.Servico;
import br.com.royal.model.Telefones;

public class ServicoDao {
	public static void cadastrarServico(Servico s) {
		String query = "INSERT INTO tbservico (nomeServico, descricaoServico,"
				+ "valorServico, atividadeServico) VALUES (?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, s.getNomeServico());
			pstm.setString(2, s.getDescricaoServico());
			pstm.setDouble(3, s.getValorServico());

			pstm.setInt(4, 1);
			pstm.execute();
		} catch (Exception e) {
			System.out.println("bugou no cadastro Servico: " + e.getMessage());
		}
	}

	public static void editarServico(Servico s, int cod) {
		String query = "UPDATE tbServico set nomeServico=?, descricaoServico=?,"
				+ "valorServico=? WHERE codServico=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, s.getNomeServico());
				pstm.setString(2, s.getDescricaoServico());
				pstm.setDouble(3, s.getValorServico());

				pstm.setInt(4, cod);

				pstm.execute();
			} catch (Exception e) {
				System.out.println("editaServico: " + e.getMessage());
			}
		}
	}

	public static void excluirServico(int cod) {
		String query = "UPDATE tbServico set atividadeServico=? WHERE codServico=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, 0);
				pstm.setInt(2, cod);
				pstm.execute();
			} catch (Exception e) {
				System.out.println("EROU: " + e.getMessage());
			}
		}
	}

	private static int getCodServico(String nomeServico) {
		String getCod = "SELECT codServico FROM tbServico WHERE nomeServico = ? and atividadeServico=?";
		Connection con = null;
		ResultSet rs = null;
		int cod = 0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(getCod);
			pstm.setString(1, "%" + nomeServico + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod = rs.getInt("codServico");
			}
		} catch (Exception e) {
			System.out.println("getCodServico: " + e.getMessage());
		}
		return cod;

	}

	public static ArrayList<Servico> getServicos() {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Servico> servicos = new ArrayList<Servico>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Servico s = new Servico();
				s.setCodServico(rs.getInt("codServico"));
				s.setNomeServico(rs.getString("nomeServico"));
				s.setDescricaoServico(rs.getString("descricaoServico"));
				s.setValorServico(rs.getDouble("valorServico"));

				servicos.add(s);
			}
			Collections.sort(servicos);
			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;

	}
	
	public static ArrayList<Servico> getServicosPorFuncionario(Funcionario f) {
		String query = "SELECT tbServico.codServico, descricaoServico, valorServico, atividadeServico, nomeServico "
				+ "from tbservicofuncionario INNER JOIN tbservico on tbservicofuncionario."
				+ "codServico=tbservico.codServico WHERE tbservico.atividadeServico=? "
				+ "AND codFuncionario=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Servico> servicos = new ArrayList<Servico>();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setInt(2, f.getCodFuncionario());
			rs = pstm.executeQuery();
			while (rs.next()) {
				Servico s = new Servico();
				s.setCodServico(rs.getInt("tbServico.codServico"));
				s.setNomeServico(rs.getString("nomeServico"));
				s.setDescricaoServico(rs.getString("descricaoServico"));
				s.setValorServico(rs.getDouble("valorServico"));

				servicos.add(s);
			}
			Collections.sort(servicos);
			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;

	}

	public static ArrayList<Servico> getServicos(String nome) {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=? AND nomeServico like ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Servico> servicos = new ArrayList<Servico>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setString(2, "%" + nome + "%");
			rs = pstm.executeQuery();
			while (rs.next()) {
				Servico s = new Servico();
				s.setCodServico(rs.getInt("codServico"));
				s.setNomeServico(rs.getString("nomeServico"));
				s.setDescricaoServico(rs.getString("descricaoServico"));
				s.setValorServico(rs.getDouble("valorServico"));

				servicos.add(s);
			}

			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;

	}

	public static Servico getServico(int cod) {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=?"
				+ " AND codServico =?";
		Connection con = null;
		ResultSet rs = null;
		Servico s = new Servico();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				s.setCodServico(rs.getInt("codServico"));
				s.setNomeServico(rs.getString("nomeServico"));
				s.setDescricaoServico(rs.getString("descricaoServico"));
				s.setValorServico(rs.getDouble("valorServico"));

			}

			return s;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;
	}

	public static String getNomeServico(String nome, int cod) {
		String query = "SELECT nomeServico FROM tbServico WHERE (nomeServico) like ?"
				+ " AND atividadeServico=1 AND codServico!=?";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getNome = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getNome = rs.getString("nomeServico");
			}
			return getNome;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade Servico;\n"
					+ e.getMessage());
		}
		return null;
	}
}
