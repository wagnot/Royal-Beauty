package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.VendaProduto;
import br.com.royal.model.VendaServico;

public class VendaProdutoDao {
	public int cadastrarVendaProduto(VendaProduto v) {
		String query = "INSERT INTO tbVendaProduto (valorTotalVendaProduto,"
				+ "codCliente, codFuncionario, codComandaProduto) VALUES (?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setDouble(1, v.getValorTotalProduto());
			pstm.setInt(2, v.getCodCliente());
			pstm.setInt(3, v.getCodFuncionarioProduto());
			pstm.setInt(4, v.getCodComandaProduto());

			pstm.execute();
			
			return getCodMaxVendaProduto();
		} catch (Exception e) {
			System.out.println("bugou no cadastro VendaProduto: "
					+ e.getMessage());
		}
		return 0;
	}
	
	public static ArrayList<VendaProduto> getVendasProduto(){
		String query = "SELECT * FROM tbVendaProduto";
		Connection con = null;
		ResultSet rs = null;
		try {
			ArrayList<VendaProduto> vendas = new ArrayList<VendaProduto>();
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				VendaProduto vp = new VendaProduto();
				vp.setCodVendaProduto(rs.getInt("codVendaProduto"));
				vp.setCodCliente(rs.getInt("codCliente"));
				vp.setCodComandaProduto(rs.getInt("codComandaProduto"));
				vp.setCodFuncionarioProduto(rs.getInt("codFuncionario"));
				vp.setValorTotalProduto(rs.getDouble("valorTotalVendaProduto"));
				vendas.add(vp);
			}
			return vendas;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	private static int getCodMaxVendaProduto(){
		String query = "SELECT codVendaProduto FROM tbVendaProduto WHERE codVendaProduto=(SELECT MAX(codVendaProduto) FROM tbVendaProduto)";
		Connection con = null;
		ResultSet rs = null;
		int cod=0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod=rs.getInt("codVendaProduto");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCodMaxVendaProduto:\n" + e.getMessage());
		}
		return 0;
	}
}
