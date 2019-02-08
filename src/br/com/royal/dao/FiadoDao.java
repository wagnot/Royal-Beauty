package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.royal.model.Fiado;
import br.com.royal.model.Produto;

public class FiadoDao {
	public void cadastrarFiado(Fiado f) {
		String query = "INSERT INTO tbFiado(dataAbate, codParcela,statusFiado) VALUES (?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, f.getDataAbate());
			pstm.setInt(2, f.getCodParcela());
			pstm.setInt(3, 0);

			pstm.execute();
		} catch (Exception e) {
			System.out.println("bugou no Cadastrar fiado: " + e.getMessage());
		}
	}
	
	public void desativarFiado(int codFiado){
		String query = "UPDATE tbFiado set statusFiado=? WHERE codFiado=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1,1);
			pstm.setInt(1,codFiado);

			pstm.execute();
		} catch (Exception e) {
			System.out.println("desativarFiado: " + e.getMessage());
		}

	}

	public ArrayList<Fiado> listarFiados() {
		String query = "SELECT codFiado,tbCliente.codCliente,codComandaProduto,codComandaServico,tbParcela.codVendaProduto,tbParcela.codVendaServico, dataAbate,nomeCliente,valorParcela FROM tbFiado INNER JOIN tbParcela on tbFiado.codParcela = tbParcela.codParcela "
				+ "INNER JOIN tbPagamento on tbParcela.codPagamento = tbPagamento.codPagamento "
				+ "INNER JOIN tbCliente on tbPagamento.codCliente = tbCliente.codCliente WHERE statusFiado = 0";

		Connection con = null;
		ArrayList<Fiado> fiados = new ArrayList<Fiado>();
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Fiado f = new Fiado();
				f.setCodFiado(rs.getInt("codFiado"));
				f.setDataAbate(rs.getString("dataAbate"));
				f.setNomeCliente(rs.getString("nomeCliente"));
				f.setValorParcela(rs.getDouble("valorParcela"));
				f.setCodComandaProduto(rs.getInt("codComandaProduto"));
				f.setCodComandaServico(rs.getInt("codComandaServico"));
				f.setCodVendaProduto(rs.getInt("codVendaProduto"));
				f.setCodVendaServico(rs.getInt("codVendaServico"));
				f.setCodCliente(rs.getInt("codCliente"));
	
				fiados.add(f);
			}
			return fiados;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Quebrou no listar fiados");
			return null;
		}
	}

	public ArrayList<Fiado> listarFiadosDia(String dataAbate) {
		String query = "SELECT codFiado,tbCliente.codCliente,codComandaServico,tbParcela.codVendaProduto,tbParcela.codVendaServico,dataAbate,nomeCliente, valorParcela FROM tbFiado INNER JOIN tbParcela on tbFiado.codParcela = tbParcela.codParcela "
				+ "INNER JOIN tbPagamento on tbParcela.codPagamento = tbPagamento.codPagamento "
				+ "INNER JOIN tbCliente on tbPagamento.codCliente = tbCliente.codCliente WHERE statusFiado = 0 and dataAbate like ?";

		Connection con = null;
		ArrayList<Fiado> fiados = new ArrayList<Fiado>();
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%"+dataAbate+"%");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Fiado f = new Fiado();
				f.setCodFiado(rs.getInt("codFiado"));
				f.setDataAbate(rs.getString("dataAbate"));
				f.setNomeCliente(rs.getString("nomeCliente"));
				f.setValorParcela(rs.getDouble("valorParcela"));
				f.setCodComandaProduto(rs.getInt("codComandaProduto"));
				f.setCodComandaServico(rs.getInt("codComandaServico"));
				f.setCodVendaProduto(rs.getInt("codVendaProduto"));
				f.setCodVendaServico(rs.getInt("codVendaServico"));
				f.setCodCliente(rs.getInt("codCliente"));
				fiados.add(f);
			}
			return fiados;

		} catch (Exception e) {
			System.out.println("Quebrou no listar fiados Dia");
			return null;
		}
	}
	
	public void pagarFiado (Fiado f){
		String query = "update tbFiado set statusFiado = ? WHERE codFiado = ?";
		Connection con = null;
		try{
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			pst.setInt(2,f.getCodFiado());
			pst.execute();
			

			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "QUEBROU NO PAGAR FIADO (UPDATE),"+e.getMessage());
		}
	}
	
	
	
}
