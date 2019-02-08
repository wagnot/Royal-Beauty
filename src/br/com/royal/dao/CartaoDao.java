package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.royal.model.Cartao;

public class CartaoDao {
	public void cadastrarCartao(Cartao cartao) {
		String query = "INSERT INTO tbCartao (codTipoCartao,codParcela) VALUES (?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cartao.getCodTipo());
			pstm.setInt(2, cartao.getCodParcela());

			pstm.execute();
		} catch (Exception e) {
			System.out.println("bugou no CadastrarCartao: " + e.getMessage());
		}
	}
}