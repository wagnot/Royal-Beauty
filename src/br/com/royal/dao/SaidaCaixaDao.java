package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.MotivoSaidaCaixa;
import br.com.royal.model.SaidaCaixa;

public class SaidaCaixaDao {
	public static void cadastrarSaidaCaixa(SaidaCaixa sc) {
		String query = "INSERT INTO tbSaidaCaixa (valorSaidaCaixa,dataSaidaCaixa, codMotivoSaidaCaixa, statusSaidaCaixa, codCaixa)"
				+ " VALUES (?, ?, ?, ?, ?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, sc.getValor());
			pst.setString(2, sc.getDataSaidaCaixa());
			pst.setInt(3, sc.getCodMotivoSaida());
			pst.setInt(4, 1);
			pst.setInt(5, sc.getCodCaixa());
			pst.execute();

		} catch (Exception e) {
			System.out.println("CadastrarSaidaCaixa" + e.getMessage());
		}
	}

//	public static void editarSaidaCaixa(SaidaCaixa f, int cod) {
//		String query = "UPDATE tbSaidaCaixa set valorSaidaCaixa = ? WHERE codSaidaCaixa = ?";
//		Connection con = null;
//		if (cod > 0) {
//			try {
//				con = Conexao.getConexao();
//				PreparedStatement pst = con.prepareStatement(query);
//				pst.setString(1, f.getDescricao());
//				pst.setInt(2, cod);
//				pst.execute();
//
//			} catch (Exception e) {
//				System.out.println("editarSaidaCaixa:\n"
//						+ e.getMessage());
//			}
//		}
//	}
//
//	public static void excluirSaidaCaixa(int cod) {
//		String query = "UPDATE tbSaidaCaixa set statusSaidaCaixa=? WHERE codSaidaCaixa=?";
//		Connection con = null;
//		if (cod > 0) {
//			try {
//				con = Conexao.getConexao();
//				PreparedStatement pst = con.prepareStatement(query);
//
//				pst.setInt(1, 0);
//				pst.setInt(2, cod);
//				pst.execute();
//
//			} catch (Exception e) {
//				System.out.println("excluirSaidaCaixa:\n"
//						+ e.getMessage());
//			}
//		}
//
//	}

	public static ArrayList<SaidaCaixa> getSaidaCaixas() {
		String query = "SELECT * from tbSaidaCaixa WHERE statusSaidaCaixa = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<SaidaCaixa> SaidaCaixas = new ArrayList<SaidaCaixa>();
			while (rs.next()) {
				SaidaCaixa f = new SaidaCaixa();
				f.setCod((rs.getInt("codSaidaCaixa")));
				f.setValor(rs.getDouble("valorSaidaCaixa"));
				f.setCodMotivoSaida(rs.getInt("codMotivoSaidaCaixa"));
				f.setDataSaidaCaixa(rs.getString("dataSaidaCaixa"));
				SaidaCaixas.add(f);
			}
			return SaidaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<SaidaCaixa> getSaidaCaixas(
			String data) {
		String query = "SELECT * from tbSaidaCaixa WHERE dataSaidaCaixa like ? AND statusSaidaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<SaidaCaixa> SaidaCaixas = new ArrayList<SaidaCaixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + data + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				SaidaCaixa f = new SaidaCaixa();
				f.setCod((rs.getInt("codSaidaCaixa")));
				f.setCodMotivoSaida(rs.getInt("codMotivoSaidaCaixa"));
				f.setValor(rs.getDouble("valorSaidaCaixa"));
				f.setDataSaidaCaixa(rs.getString("dataSaidaCaixa"));
				SaidaCaixas.add(f);				
				System.out.println(f.getValor());
			}
			return SaidaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static ArrayList<SaidaCaixa> getSaidaCaixasPCodCaixa(int codCaixa) {
		String query = "SELECT * from tbSaidaCaixa WHERE codCaixa = ? AND statusSaidaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<SaidaCaixa> SaidaCaixas = new ArrayList<SaidaCaixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, codCaixa);
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				SaidaCaixa f = new SaidaCaixa();
				f.setCod((rs.getInt("codSaidaCaixa")));
				f.setCodMotivoSaida(rs.getInt("codMotivoSaidaCaixa"));
				f.setValor(rs.getDouble("valorSaidaCaixa"));
				f.setDataSaidaCaixa(rs.getString("dataSaidaCaixa"));
				SaidaCaixas.add(f);				
				System.out.println(f.getValor());
			}
			return SaidaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static SaidaCaixa getSaidaCaixa(int cod) {
		String query = "SELECT * FROM tbSaidaCaixa WHERE codSaidaCaixa=? AND statusSaidaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		SaidaCaixa f = new SaidaCaixa();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCod((rs.getInt("codSaidaCaixa")));
				f.setValor(rs.getDouble("valorSaidaCaixa"));
				f.setCodMotivoSaida(rs.getInt("codMotivoSaidaCaixa"));
				f.setDataSaidaCaixa(rs.getString("dataSaidaCaixa"));
			}
			return f;
		} catch (Exception e) {
			System.out.println("bugou no getSaidaCaixa;\n"
					+ e.getMessage());
		}
		return null;
	}
	
	
	
	public static MotivoSaidaCaixa getMotivoSaidaCaixa(String desc) {
		String query = "SELECT * FROM tbMotivoSaidaCaixa WHERE descricaoMotivoSaidaCaixa like ? AND statusMotivoSaidaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		MotivoSaidaCaixa f = new MotivoSaidaCaixa();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, desc);
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

}
