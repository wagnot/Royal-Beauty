package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.royal.model.EntradaProduto;
import br.com.royal.model.Fabricante;

public class FabricanteDao {

	public boolean conferirNome(Fabricante f) {
		String query = "SELECT * FROM tbFabricante WHERE nomeFabricante = ? ";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, f.getNomeFabricante());

			ResultSet rs = pst.executeQuery();
			while (!rs.next()) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("bugou no conferir" + e.getMessage());
		}
		return false;
	}

	public void inserirFabricante(Fabricante f) {
		String query = "INSERT INTO tbFabricante (nomeFabricante)"
				+ "VALUES(?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, f.getNomeFabricante());
			pst.execute();
		} catch (Exception e) {
			System.out.println("oeaea mashgauysgya" + e.getMessage());
		}
	}
	
	public boolean editar(Fabricante ep, String nome) {
		String query = "UPDATE tbFabricante set nomeFabricante=?  WHERE nomeFabricante = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, ep.getNomeFabricante());
			pst.setString(2, nome);
			pst.execute();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
	
	
	
	

	

	public static Fabricante inserirJcombo(Fabricante f) {
		String query = "SELECT * from tbFabricante WHERE nomeFabricante = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, f.getNomeFabricante());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				f.setCodFabricante((rs.getInt("codFabricante")));
				f.setNomeFabricante(rs.getString("nomeFabricante"));

			}
			return f;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static List<Fabricante> listar() {
		String query = "SELECT * from tbFabricante";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			List<Fabricante> fabricante = new ArrayList<Fabricante>();
			while (rs.next()) {

				Fabricante f = new Fabricante();
				f.setCodFabricante((rs.getInt("codFabricante")));
				f.setNomeFabricante(rs.getString("nomeFabricante"));

				fabricante.add(f);
			}
			return fabricante;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static List<Fabricante> find(String nome) {
		String query = "SELECT * from tbFabricante WHERE nomeFabricante LIKE ?";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%" + nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Fabricante> fabricante = new ArrayList<Fabricante>();
			while (rs.next()) {

				Fabricante f = new Fabricante();
				f.setCodFabricante((rs.getInt("codFabricante")));
				f.setNomeFabricante(rs.getString("nomeFabricante"));

				fabricante.add(f);
			}
			return fabricante;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	

}
