package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.royal.model.Agendamento;
import br.com.royal.model.Horario;

public class AgendamentoDao {
	public static void inserir(Agendamento a){
		String query = "INSERT INTO tbAgendamento (dataAgendamento, codCliente, codHorarioInicio, "
				+ "horarioFim, codServico, codFuncionario, statusAgendamento) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(a.getDataAgendamento().getTime()));
			pst.setInt(2, a.getCodigoCliente());
			pst.setDouble(3, a.getCodigoHorarioInicio());
			pst.setString(4, a.getHorarioFim());
			pst.setInt(5, a.getCodigoServico());
			pst.setInt(6, a.getCodigoFuncionario());
			pst.setInt(7, 1);
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static ArrayList<Agendamento> listar(Calendar data){
		String query = "SELECT * from tbAgendamento WHERE dataAgendamento LIKE ? AND "
				+ "statusAgendamento = ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(data.getTime().getTime()));
			pst.setInt(2, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<Agendamento> agendamentos = new ArrayList<Agendamento>();
			while (rs.next()) {
				Agendamento a = new Agendamento();
				a.setCodigo(rs.getInt("codAgendamento"));
				a.setDataAgendamento(rs.getDate("dataAgendamento"));
				a.setCodigoCliente(rs.getInt("codCliente"));
				a.setCodigoServico(rs.getInt("codServico"));
				a.setCodigoHorarioInicio(rs.getInt("codHorarioInicio"));
				a.setHorarioFim(rs.getString("horarioFim"));
				a.setCodigoFuncionario(rs.getInt("codFuncionario"));
				a.setStatus(rs.getInt("statusAgendamento"));
				agendamentos.add(a);
			}
			return agendamentos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void editar(Agendamento a){
		String query = "UPDATE tbAgendamento set codCliente=?, horarioFim=?, codServico=?"
				+ " WHERE codAgendamento=? AND statusAgendamento=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, a.getCodigoCliente());
			pst.setString(2, a.getHorarioFim());
			pst.setInt(3, a.getCodigoServico());
			pst.setInt(4, a.getCodigo());
			pst.setInt(5, a.getStatus());
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void excluir(Agendamento a){
		String query = "UPDATE tbAgendamento set statusAgendamento=? WHERE codAgendamento=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 0);
			pst.setInt(2, a.getCodigo());
			pst.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static ArrayList<Agendamento> verificaIntervaloAgendamentos(Agendamento a){
		String query = "SELECT * FROM tbAgendamento WHERE codFuncionario= ? AND "
				+ "dataAgendamento = ? AND codAgendamento!=? AND statusAgendamento=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, a.getCodigoFuncionario());
			pst.setDate(2, new java.sql.Date(a.getDataAgendamento().getTime()));
			pst.setInt(3, a.getCodigo());
			pst.setInt(4, 1);
			ResultSet rs = pst.executeQuery();
			ArrayList<Agendamento> agendamentos = new ArrayList<Agendamento>();
			while (rs.next()) {
				Agendamento ag = new Agendamento();
				ag.setCodigo(rs.getInt("codAgendamento"));
				ag.setDataAgendamento(rs.getDate("dataAgendamento"));
				ag.setCodigoCliente(rs.getInt("codCliente"));
				ag.setCodigoServico(rs.getInt("codServico"));
				ag.setCodigoHorarioInicio(rs.getInt("codHorarioInicio"));
				ag.setHorarioFim(rs.getString("horarioFim"));
				ag.setCodigoFuncionario(rs.getInt("codFuncionario"));
				ag.setStatus(rs.getInt("statusAgendamento"));
				agendamentos.add(ag);
			}
			return agendamentos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static Agendamento encontraInfoAgendamento(Agendamento a, Calendar data){
		for (Agendamento ag : listar(data)){
			if (ag.getHorarioFim().equals(a.getHorarioFim()) &&
				ag.getCodigoHorarioInicio()==a.getCodigoHorarioInicio() 
				&& ag.getCodigoFuncionario()==a.getCodigoFuncionario()){
				return ag;
			}
		}
		return null;
	}
}
