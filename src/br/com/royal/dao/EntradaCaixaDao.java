package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.EntradaCaixa;
import br.com.royal.model.MotivoEntradaCaixa;


public class EntradaCaixaDao {
	public static void cadastrarEntradaCaixa(EntradaCaixa sc) {
		String query = "INSERT INTO tbEntradaCaixa (valorEntradaCaixa,dataEntradaCaixa, codMotivoEntradaCaixa, statusEntradaCaixa, codFormaDePagamento, codCaixa)"
				+ " VALUES (?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, sc.getValor());
			pst.setString(2, sc.getDataEntradaCaixa());
			pst.setInt(3, sc.getCodMotivoEntrada());
			pst.setInt(4, 1);
			pst.setInt(5, sc.getCodFormaDePagamento());
			pst.setInt(6, sc.getCodCaixa());
			
			pst.execute();

		} catch (Exception e) {
			System.out.println("CadastrarEntradaCaixa" + e.getMessage());
		}
	}

//	public static void editarEntradaCaixa(EntradaCaixa f, int cod) {
//		String query = "UPDATE tbEntradaCaixa set valorEntradaCaixa = ? WHERE codEntradaCaixa = ?";
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
//				System.out.println("editarEntradaCaixa:\n"
//						+ e.getMessage());
//			}
//		}
//	}
//
//	public static void excluirEntradaCaixa(int cod) {
//		String query = "UPDATE tbEntradaCaixa set statusEntradaCaixa=? WHERE codEntradaCaixa=?";
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
//				System.out.println("excluirEntradaCaixa:\n"
//						+ e.getMessage());
//			}
//		}
//
//	}

	public static ArrayList<EntradaCaixa> getEntradaCaixas() {
		String query = "SELECT * from tbEntradaCaixa WHERE statusEntradaCaixa = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<EntradaCaixa> EntradaCaixas = new ArrayList<EntradaCaixa>();
			while (rs.next()) {
				EntradaCaixa f = new EntradaCaixa();
				f.setCod((rs.getInt("codEntradaCaixa")));
				f.setValor(rs.getDouble("valorEntradaCaixa"));
				f.setCodMotivoEntrada(rs.getInt("codMotivoEntradaCaixa"));
				f.setDataEntradaCaixa(rs.getString("dataEntradaCaixa"));
				f.setCodFormaDePagamento(rs.getInt("codFormaDePagamento"));
				f.setCodCaixa(rs.getInt("codCaixa"));
				EntradaCaixas.add(f);
			}
			return EntradaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<EntradaCaixa> getEntradaCaixas(String data) {
		String query = "SELECT * from tbEntradaCaixa WHERE dataEntradaCaixa like ? AND statusEntradaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<EntradaCaixa> EntradaCaixas = new ArrayList<EntradaCaixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + data + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				EntradaCaixa f = new EntradaCaixa();
				f.setCod((rs.getInt("codEntradaCaixa")));
				f.setCodMotivoEntrada(rs.getInt("codMotivoEntradaCaixa"));
				f.setValor(rs.getDouble("valorEntradaCaixa"));
				f.setDataEntradaCaixa(rs.getString("dataEntradaCaixa"));
				f.setCodFormaDePagamento(rs.getInt("codFormaDePagamento"));
				f.setCodCaixa(rs.getInt("codCaixa"));
				EntradaCaixas.add(f);				
			}
			return EntradaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static ArrayList<EntradaCaixa> getEntradaCaixasPCodCaixa(int codCaixa) {
		String query = "SELECT * from tbEntradaCaixa WHERE codCaixa=? AND statusEntradaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<EntradaCaixa> EntradaCaixas = new ArrayList<EntradaCaixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, codCaixa);
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				EntradaCaixa f = new EntradaCaixa();
				f.setCod((rs.getInt("codEntradaCaixa")));
				f.setCodMotivoEntrada(rs.getInt("codMotivoEntradaCaixa"));
				f.setValor(rs.getDouble("valorEntradaCaixa"));
				f.setDataEntradaCaixa(rs.getString("dataEntradaCaixa"));
				f.setCodFormaDePagamento(rs.getInt("codFormaDePagamento"));
				f.setCodCaixa(rs.getInt("codCaixa"));
				EntradaCaixas.add(f);				
			}
			return EntradaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static EntradaCaixa getEntradaCaixa(int cod) {
		String query = "SELECT * FROM tbEntradaCaixa WHERE codEntradaCaixa=? AND statusEntradaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		EntradaCaixa f = new EntradaCaixa();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCod((rs.getInt("codEntradaCaixa")));
				f.setValor(rs.getDouble("valorEntradaCaixa"));
				f.setCodMotivoEntrada(rs.getInt("codMotivoEntradaCaixa"));
				f.setDataEntradaCaixa(rs.getString("dataEntradaCaixa"));
				f.setCodFormaDePagamento(rs.getInt("codFormaDePagamento"));
				f.setCodCaixa(rs.getInt("codCaixa"));
			}
			return f;
		} catch (Exception e) {
			System.out.println("bugou no getEntradaCaixa;\n"
					+ e.getMessage());
		}
		return null;
	}
	
	public static MotivoEntradaCaixa getMotivoEntradaCaixa(String desc) {
		String query = "SELECT * FROM tbMotivoEntradaCaixa WHERE descricaoMotivoEntradaCaixa like ? AND statusMotivoEntradaCaixa = ?";
		Connection con = null;
		ResultSet rs = null;
		MotivoEntradaCaixa f = new MotivoEntradaCaixa();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, desc);
			pstm.setInt(2, 1);

			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCod(rs.getInt("codMotivoEntradaCaixa"));
				f.setDescricao(rs.getString("descricaoMotivoEntradaCaixa"));
			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getMotivoEntradaCaixa;\n"
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
				f.setDescricao(rs.getString("descricaoMotivoEntradaCaixa"));

			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getMotivoEntradaCaixa;\n"
					+ e.getMessage());
		}
		return null;
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
				f.setDescricao(rs.getString("descricaoMotivoEntradaCaixa"));
				MotivoEntradaCaixas.add(f);

			}
			return MotivoEntradaCaixas;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	
}
