package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import br.com.royal.model.Horario;

public class HorarioDao {
	public static void inserir(Horario h) {
		String query = "INSERT INTO tbHorario (horaInicio, dataHorario, statusHorario) VALUES (?, ?, ?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, h.getHoraInicio());
			pst.setString(2, h.getDataHorario());
			pst.setInt(3, 1);
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Horario buscaIDHorario(Horario h) {
		String query = "SELECT codHorario FROM tbHorario WHERE horaInicio = ? AND dataHorario LIKE ?"
				+ " AND statusHorario = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, h.getHoraInicio());
			pst.setString(2, h.getDataHorario());
			pst.setInt(3, 1);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				h.setCodHorario(rs.getInt("codHorario"));
				return h;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Horario> listarGerais() {
		ArrayList<Horario> horarios = new ArrayList<Horario>();
		String query = "SELECT * FROM tbHorario WHERE statusHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Horario h = new Horario();
				h.setCodHorario(rs.getInt("codHorario"));
				h.setHoraInicio((rs.getString("horaInicio")));
				h.setDataHorario(rs.getString("dataHorario"));
				h.setStatus(rs.getInt("statusHorario"));
				horarios.add(h);
			}
			Collections.sort(horarios);
			return horarios;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public static ArrayList<Horario> listarNaoEspecificos() {
		ArrayList<Horario> horarios = new ArrayList<Horario>();
		String query = "SELECT * FROM tbHorario WHERE dataHorario like ? AND statusHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "TODAS");
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Horario h = new Horario();
				h.setCodHorario(rs.getInt("codHorario"));
				h.setHoraInicio((rs.getString("horaInicio")));
				h.setDataHorario(rs.getString("dataHorario"));
				h.setStatus(rs.getInt("statusHorario"));
				horarios.add(h);
			}
			Collections.sort(horarios);
			return horarios;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Horario> listarEspecificos(Horario h) {
		ArrayList<Horario> horarios = new ArrayList<Horario>();
		String query = "SELECT * FROM tbHorario WHERE dataHorario like ? AND statusHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, h.getDataHorario());
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Horario hr = new Horario();
				hr.setCodHorario(rs.getInt("codHorario"));
				hr.setHoraInicio((rs.getString("horaInicio")));
				hr.setDataHorario(rs.getString("dataHorario"));
				hr.setStatus(rs.getInt("statusHorario"));
				horarios.add(hr);
			}
			Collections.sort(horarios);
			return horarios;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void editar(Horario h) {
		String query = "UPDATE tbHorario set horaInicio = ? WHERE codHorario = ? AND statusHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, h.getHoraInicio());
			pst.setInt(2, h.getCodHorario());
			pst.setInt(3, 1);
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void excluir(Horario h) {
		String query = "UPDATE tbHorario set statusHorario=? WHERE codHorario = ? AND statusHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 0);
			pst.setInt(2, h.getCodHorario());
			pst.setInt(3, 1);
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static Horario verificaSubstituicao(Horario h){
		for (Horario hor : listarGerais()) {
			buscaIDHorario(hor);
			if (hor.getCodHorario() != h.getCodHorario()) {
				if (hor.toString().equals(h.toString()) && h.getDataHorario().equals("TODAS")) 
					return hor;
			}
		}

		return null;
	}
	
	public static void setaHorarioPraTodasAsDatas(Horario h){
		String query = "UPDATE tbHorario set dataHorario=? WHERE horaInicio LIKE ? AND statusHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "TODAS");
			pst.setString(2, h.getHoraInicio());
			pst.setInt(3, 1);
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Horario jaExisteInativo(Horario h){
		String query = "SELECT * FROM tbHorario WHERE horaInicio = ? AND dataHorario LIKE ?"
				+ " AND statusHorario = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, h.getHoraInicio());
			pst.setString(2, h.getDataHorario());
			pst.setInt(3, 0);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				h.setCodHorario(rs.getInt("codHorario"));
				h.setHoraInicio(rs.getString("horaInicio"));
				h.setStatus(rs.getInt("statusHorario"));
				return h;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void reativaHorario(Horario h){
		String query = "UPDATE tbHorario set statusHorario = ? WHERE codHorario = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			pst.setInt(2, h.getCodHorario());
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Horario jaExiste(Horario h) {
		for (Horario hor : listarGerais()) {
			if (hor.toString().equals(h.toString()) && (hor.getDataHorario().
				equals(h.getDataHorario()) || hor.getDataHorario().equals("TODAS")))
				return hor;
		}
		return null;
	}

	public static Horario verificaDuplicidadeNaEdicao(Horario h) {
		for (Horario hor : listarGerais()) {
			buscaIDHorario(hor);
			if (hor.getCodHorario() != h.getCodHorario()) {
				if (hor.toString().equals(h.toString()) && hor.getDataHorario().
													equals(h.getDataHorario())) 
					return hor;
			}
		}

		return null;
	}
	
	public static Horario encontrePorID(Horario h){
		String query = "SELECT horaInicio FROM tbHorario WHERE codHorario=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, h.getCodHorario());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Horario hor = new Horario();
				hor.setHoraInicio((rs.getString("horaInicio")));
				return hor;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Horario> pesquisaHorario(String horario) {
		ArrayList<Horario> horariosEncontrados = new ArrayList<Horario>();
		for (Horario hor : listarGerais()) {
			if (hor.toString().toLowerCase().contains(horario.toLowerCase()))
				horariosEncontrados.add(hor);
		}
		return horariosEncontrados;
	}
}
