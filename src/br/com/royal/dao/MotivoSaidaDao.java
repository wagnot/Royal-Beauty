package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import br.com.royal.model.MotivoSaida;

public class MotivoSaidaDao {
	
	public boolean conferirMotivo(MotivoSaida ms) {
		String query = "SELECT * FROM tbMotivoSaidaProduto WHERE descricaoMotivoSaidaProduto = ? ";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, ms.getDescricaoMotivoSaida());

			ResultSet rs = pst.executeQuery();
			while (!rs.next()) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("bugou no conferir" + e.getMessage());
		}
		return false;
	}

	public void inserirMotivo(MotivoSaida ms) {
		String query = "INSERT INTO tbMotivoSaidaProduto (descricaoMotivoSaidaProduto)"
				+ "VALUES(?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, ms.getDescricaoMotivoSaida());
			pst.execute();
		} catch (Exception e) {
			System.out.println("oeaea mashgauysgya" + e.getMessage());
		}
	}

	public boolean editar(MotivoSaida ms, String descricao) {
		String query = "UPDATE tbMotivoSaidaProduto set descricaoMotivoSaidaProduto=?  WHERE descricaoMotivoSaidaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, ms.getDescricaoMotivoSaida());
			pst.setString(2, descricao);
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public static List<MotivoSaida> listar() {
		String query = "SELECT * from tbMotivoSaidaProduto";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			List<MotivoSaida> motivo = new ArrayList<MotivoSaida>();
			while (rs.next()) {

				MotivoSaida em = new MotivoSaida();
				em.setCodMotivoSaida((rs.getInt("codMotivoSaidaProduto")));
				em.setDescricaoMotivoSaida(rs.getString("descricaoMotivoSaidaProduto"));

				motivo.add(em);
			}
			return motivo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static List<MotivoSaida> find(String descricao) {
		String query = "SELECT * from tbMotivoSaidaProduto WHERE descricaoMotivoSaidaProduto LIKE ?";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%" + descricao + "%");
			ResultSet rs = pst.executeQuery();
			List<MotivoSaida> motivo = new ArrayList<MotivoSaida>();
			while (rs.next()) {

				MotivoSaida em = new MotivoSaida();
				em.setCodMotivoSaida((rs.getInt("codMotivoSaidaProduto")));
				em.setDescricaoMotivoSaida(rs.getString("descricaoMotivoSaidaProduto"));

				motivo.add(em);
			}
			return motivo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static MotivoSaida inserirJcombo(MotivoSaida me) {
		String query = "SELECT * from tbMotivoSaidaProduto WHERE descricaoMotivoSaidaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, me.getDescricaoMotivoSaida());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				me.setCodMotivoSaida((rs.getInt("codMotivoSaidaProduto")));
				me.setDescricaoMotivoSaida(rs.getString("descricaoMotivoSaidaProduto"));

			}
			return me;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public MotivoSaida pegarMotivo(String descricao){
		String query = "SELECT * FROM tbMotivoSaidaProduto WHERE descricaoMotivoSaidaProduto LIKE ?";
		Connection con = null;
		try{
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%"+ descricao + "%");
			ResultSet rs = pstm.executeQuery();
			MotivoSaida ms = new MotivoSaida();
			while (rs.next()) {
				ms.setCodMotivoSaida((rs.getInt("codMotivoSaidaProduto")));
				ms.setDescricaoMotivoSaida(rs.getString("descricaoMotivoSaidaProduto"));

			}
			return ms;
			
		}catch(Exception e){
			System.out.println("quebrou no pegar motivo");
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
