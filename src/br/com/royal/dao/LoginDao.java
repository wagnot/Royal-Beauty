package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.royal.model.Login;

public class LoginDao {
	public String acesso;

	public void recuperar(Login l) {
		String query = "UPDATE tbuser SET senha = ? WHERE resposta like = ";
		Connection con = null;

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);

			pstm.setString(1, l.getPergunta());
			pstm.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Login logar(Login login) {
		String query = "SELECT loginUsuario ,senhaUsuario FROM tbusuario "
				+ "WHERE loginUsuario like ? and senhaUsuario like ? and usuarioAtivo=1";
		Connection con = null;
		Login l = new Login();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, login.getUser());
			pstm.setString(2, login.getSenha());
			ResultSet rs;
			rs = pstm.executeQuery();

			if (rs.next()) {
				l.setUser(rs.getString("loginUsuario"));
				l.setSenha(rs.getString("senhaUsuario"));
			}
			return l;

		} catch (Exception e) {
			System.out.println("ERRO: " + e.getMessage());
		}
		return null;

	}

	public static Login getUsuario(String login) {
		String query = "SELECT * FROM tbusuario WHERE loginUsuario =? AND usuarioAtivo=1";
		Connection con = null;
		Login l = new Login();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, login);
			ResultSet rs;
			rs = pstm.executeQuery();

			if (rs.next()) {
				l.setId(rs.getInt("codUsuario"));
				l.setNome(rs.getString("loginUsuario"));
				l.setPergunta(rs.getString("perguntaUsuario"));
				l.setResposta(rs.getString("respostaSecreta"));
				System.out.println(rs.getInt("codUsuario"));
			}
			return l;

		} catch (Exception e) {
			System.out.println("ERRO getUsuario: " + e.getMessage());
		}
		return null;
	}

	public static void editarUsuario(Login l) {
		String query = "UPDATE tbusuario set senhaUsuario=? WHERE codUsuario=?";
		Connection con = null;
		int cod = getUsuario(l.getNome()).getId();
		System.out.println(cod);
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, l.getSenha());
				pstm.setInt(2, cod);

				pstm.execute();
			} catch (Exception e) {
				System.out.println("editaUsuario: " + e.getMessage());
			}
		}
	}

}
