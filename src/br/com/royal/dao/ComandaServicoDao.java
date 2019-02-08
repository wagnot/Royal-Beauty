package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.ComandaProduto;
import br.com.royal.model.ComandaServico;
import br.com.royal.model.Servico;
import br.com.royal.model.ServicoComanda;

public class ComandaServicoDao {

	public static int cadastrarComandaServico(ComandaServico comanda){
		String query = "INSERT INTO tbComandaServico(valorTotalVendaServico, dataComandaServico, observacao)"
				+ "VALUES(?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, comanda.getSubTotalComanda());
			pst.setString(2, comanda.getDataComanda());
			pst.setString(3, comanda.getObservacao());
			pst.execute();
			
			return getCodMaxComandaServico();
		} catch (Exception e) {
			System.out.println("erro cadastrarComandaServico:\n" + e.getMessage());
		}
		return 0;
	}
	
	public static ArrayList<ComandaServico> listar(){
		ArrayList<ComandaServico> comandas = new ArrayList<ComandaServico>();
		String query = "SELECT * FROM tbComandaServico";
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				ComandaServico cs = new ComandaServico();
				cs.setComandaServico(rs.getInt("codComandaServico"));
				cs.setDataComanda(rs.getString("dataComandaServico"));
				cs.setSubTotalComanda(rs.getDouble("valorTotalVendaServico"));
				cs.setObservacao(rs.getString("observacao"));
				comandas.add(cs);
			}
			return comandas;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private static int getCodMaxComandaServico(){
		String query = "SELECT codComandaServico FROM tbComandaServico WHERE codComandaServico=(SELECT MAX(codComandaServico) FROM tbComandaServico)";
		Connection con = null;
		ResultSet rs = null;
		int cod=0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod=rs.getInt("codComandaServico");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCodMaxComandaServico;\n" + e.getMessage());
		}
		return 0;
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

	public static ArrayList<Servico> getServicosLimitados(String nome,
			ArrayList<ServicoComanda> nomesServicos) {
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
				if (verificaNome(rs.getString("nomeServico"), nomesServicos)) {
					Servico s = new Servico();
					s.setCodServico(rs.getInt("codServico"));
					s.setNomeServico(rs.getString("nomeServico"));
					s.setDescricaoServico(rs.getString("descricaoServico"));
					s.setValorServico(rs.getDouble("valorServico"));

					servicos.add(s);
				}
			}

			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Servico> getServicosLimitados(
			ArrayList<ServicoComanda> nomesServicos) {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=? ";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Servico> servicos = new ArrayList<Servico>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				if (verificaNome(rs.getString("nomeServico"), nomesServicos)) {
					Servico s = new Servico();
					s.setCodServico(rs.getInt("codServico"));
					s.setNomeServico(rs.getString("nomeServico"));
					s.setDescricaoServico(rs.getString("descricaoServico"));
					s.setValorServico(rs.getDouble("valorServico"));

					servicos.add(s);
				}
			}

			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;
	}

	private static boolean verificaNome(String nome,
			ArrayList<ServicoComanda> nomesServicos) {
		for (ServicoComanda s : nomesServicos) {
			if (nome.equals(s.getNome())) {
				return false;
			}
		}
		return true;
	}

	public static Servico getServico(String nome) {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=?"
				+ " AND nomeServico =?";
		Connection con = null;
		ResultSet rs = null;
		Servico s = new Servico();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setString(2, nome);
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

			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;

	}
	
	public static ArrayList<Servico> getServicosPComanda(ComandaServico cs) {
		String query = "SELECT * FROM tbItensComandaServico WHERE codComandaServico=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Servico> servicos = new ArrayList<Servico>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cs.getComandaServico());
			rs = pstm.executeQuery();
			while (rs.next()) {
				Servico s = new Servico();
				s.setCodServico(rs.getInt("codServico"));
				servicos.add(s);
			}

			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;

	}

}
