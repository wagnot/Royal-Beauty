package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.royal.model.Cheque;

public class ChequeDao {
	public void cadastrarCheque(Cheque cheque) {
		String query = "INSERT INTO tbCheque(nomeBanco, numeroCheque,codParcela) VALUES (?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, cheque.getNomeBanco());
			pstm.setString(2, cheque.getNumeroCheque());
			pstm.setInt(3, cheque.getCodParcela());

			pstm.execute();
		} catch (Exception e) {
			System.out.println("bugou no CadastrarCheque: " + e.getMessage());
		}
	}
}