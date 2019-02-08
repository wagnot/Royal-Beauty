package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.VendaServico;

public class VendaServicoDao {
	public int cadastrarVendaServico(VendaServico s) {
		String query = "INSERT INTO tbVendaServico (valorTotalVendaServico,"
				+ " codCliente, codFuncionario, codComandaServico) VALUES (?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setDouble(1, s.getValorTotalServico());
			pstm.setInt(2, s.getCodCliente());
			pstm.setInt(3, s.getCodFuncionario());
			pstm.setInt(4, s.getCodComandaServico());
			
			pstm.execute();
			
			return getCodMaxVendaServico();
		} catch (Exception e) {
			System.out.println("bugou no cadastro VendaServico: " + e.getMessage());
		}
		return 0;
	}
	
	public static ArrayList<VendaServico> getVendasServico(){
		String query = "SELECT * FROM tbVendaServico";
		Connection con = null;
		ResultSet rs = null;
		try {
			ArrayList<VendaServico> vendas = new ArrayList<VendaServico>();
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				VendaServico vs = new VendaServico();
				vs.setCodVendaServico(rs.getInt("codVendaServico"));
				vs.setCodCliente(rs.getInt("codCliente"));
				vs.setCodComandaServico(rs.getInt("codComandaServico"));
				vs.setCodFuncionario(rs.getInt("codFuncionario"));
				vs.setValorTotalServico(rs.getDouble("valorTotalVendaServico"));
				vendas.add(vs);
			}
			return vendas;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	private static int getCodMaxVendaServico(){
		String query = "SELECT codVendaServico FROM tbVendaServico WHERE codVendaServico=(SELECT MAX(codVendaServico) FROM tbVendaServico)";
		Connection con = null;
		ResultSet rs = null;
		int cod=0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod=rs.getInt("codVendaServico");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCodMaxVendaServico:\n" + e.getMessage());
		}
		return 0;
	}

}
