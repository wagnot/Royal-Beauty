package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.royal.model.Fabricante;
import br.com.royal.model.MotivoEntrada;

public class MotivoEntradaDao {
	public boolean conferirMotivo(MotivoEntrada em) {
		String query = "SELECT * FROM tbMotivoEntradaProduto WHERE descricaoEntradaProduto = ? ";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, em.getDescricao());

			ResultSet rs = pst.executeQuery();
			while (!rs.next()) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("bugou no conferir" + e.getMessage());
		}
		return false;
	}

	public void inserirMotivo(MotivoEntrada me) {
		String query = "INSERT INTO tbMotivoEntradaProduto (descricaoEntradaProduto)"
				+ "VALUES(?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, me.getDescricao());
			pst.execute();
		} catch (Exception e) {
			System.out.println("oeaea mashgauysgya" + e.getMessage());
		}
	}

	public boolean editar(MotivoEntrada me, String descricao) {
		String query = "UPDATE tbMotivoEntradaProduto set descricaoEntradaProduto=?  WHERE descricaoEntradaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, me.getDescricao());
			pst.setString(2, descricao);
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public static List<MotivoEntrada> listar() {
		String query = "SELECT * from tbMotivoEntradaProduto";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			List<MotivoEntrada> motivo = new ArrayList<MotivoEntrada>();
			while (rs.next()) {

				MotivoEntrada em = new MotivoEntrada();
				em.setCodMotivoEntrada((rs.getInt("codMotivoEntradaProduto")));
				em.setDescricao(rs.getString("descricaoEntradaProduto"));

				motivo.add(em);
			}
			return motivo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static List<MotivoEntrada> find(String descricao) {
		String query = "SELECT * from tbMotivoEntradaProduto WHERE descricaoEntradaProduto LIKE ?";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%" + descricao + "%");
			ResultSet rs = pst.executeQuery();
			List<MotivoEntrada> motivo = new ArrayList<MotivoEntrada>();
			while (rs.next()) {

				MotivoEntrada em = new MotivoEntrada();
				em.setCodMotivoEntrada((rs.getInt("codMotivoEntradaProduto")));
				em.setDescricao(rs.getString("descricaoEntradaProduto"));

				motivo.add(em);
			}
			return motivo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static MotivoEntrada inserirJcombo(MotivoEntrada me) {
		String query = "SELECT * from tbMotivoEntradaProduto WHERE descricaoEntradaProduto = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, me.getDescricao());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				me.setCodMotivoEntrada((rs.getInt("codMotivoEntradaProduto")));
				me.setDescricao(rs.getString("descricaoEntradaProduto"));

			}
			return me;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}