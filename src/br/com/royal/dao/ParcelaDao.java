package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.Pagamento;
import br.com.royal.model.Parcela;
import br.com.royal.model.QuantidadeParcela;

public class ParcelaDao {
	public int cadastrarParcela(Parcela parcela) {
		String query = "INSERT INTO tbParcela (codFormaDePagamento, codQuantidadeParcela, valorParcela,"
				+ "codVendaProduto, codVendaServico, codPagamento,dataVencimento) VALUES (?,?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, parcela.getCodFormaDePagamento());
			pstm.setInt(2, parcela.getCodQuantidadeParcela());
			pstm.setDouble(3, parcela.getValorParcela());
			pstm.setInt(4, parcela.getCodVendaProduto());
			pstm.setInt(5, parcela.getCodVendaServico());
			pstm.setInt(6, parcela.getCodPagamento());
			pstm.setString(7, parcela.getDataVencimento());

			pstm.execute();
			
			return getCodMaxParcela();
		} catch (Exception e) {
			System.out.println("bugou no cadastrarParcela:\n"
					+ e.getMessage());
		}
		return 0;
	}

	private int getCodMaxParcela(){
		String query = "SELECT codParcela FROM tbParcela WHERE codParcela=(SELECT MAX(codParcela) FROM tbParcela)";
		Connection con = null;
		ResultSet rs = null;
		int cod=0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod=rs.getInt("codParcela");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCodMaxParcela:\n" + e.getMessage());
		}
		return 0;
	}
}
