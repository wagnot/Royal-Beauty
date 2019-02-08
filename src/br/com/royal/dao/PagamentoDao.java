package br.com.royal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.royal.model.Pagamento;

import java.sql.Connection;

public class PagamentoDao {
	public int cadastrarPagamento(Pagamento pagamento){
		String query="INSERT INTO tbPagamento (codComandaProduto, codComandaServico, totalPagamento,"
				+ "codCliente, desconto, dataPagamento) VALUES (?,?,?,?,?,?)";
		Connection con= null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, pagamento.getCodComandaProduto());
			pstm.setInt(2, pagamento.getCodComandaServico());
			pstm.setDouble(3, pagamento.getTotalPagamento());
			pstm.setInt(4, pagamento.getCodCliente());
			pstm.setDouble(5, pagamento.getDesconto());
			pstm.setString(6, pagamento.getDataPagamento());
			
			pstm.execute();
			
			return this.getCodMaxComandaPagamento();
		} catch (Exception e) {
			System.out.println("bugou no cadastrarPagamento:\n" + e.getMessage());
		}
		return 0;
	}

	private int getCodMaxComandaPagamento(){
		String query = "SELECT codPagamento FROM tbPagamento WHERE codPagamento=(SELECT MAX(codPagamento) FROM tbPagamento)";
		Connection con = null;
		ResultSet rs = null;
		int cod=0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod=rs.getInt("codPagamento");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCodMaxComandaPagamento:\n" + e.getMessage());
		}
		return 0;
	}

}
