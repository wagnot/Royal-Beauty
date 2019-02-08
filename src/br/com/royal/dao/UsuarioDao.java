package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.Funcionario;
import br.com.royal.model.Telefones;
import br.com.royal.model.Usuario;

public class UsuarioDao {
	public static void cadastroUsuario(Usuario u) {
		String query = "INSERT INTO tbusuario(loginUsuario, senhaUsuario, codFuncionario,"
				+ "tipoUsuario, perguntaUsuario, respostaSecreta, usuarioAtivo) VALUES (?,?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, u.getLogin());
			pstm.setString(2, u.getSenha());
			pstm.setInt(3, u.getFuncionario().getCodFuncionario());
			pstm.setString(4, u.getTipo());
			pstm.setString(5, u.getPergunta());
			pstm.setString(6, u.getResposta());

			pstm.setInt(7, 1);
			pstm.execute();
		} catch (Exception e) {
			System.err.println("bugou no cadastro de Usuario: "
					+ e.getMessage());
		}
	}

	public static void editarUsuario(Usuario u, int cod) {
		String query = "UPDATE tbusuario set loginUsuario=?, senhaUsuario=?, "
				+ "tipoUsuario=?, perguntaUsuario=?, respostaSecreta=? WHERE codUsuario=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, u.getLogin());
				pstm.setString(2, u.getSenha());
				pstm.setString(3, u.getTipo());
				pstm.setString(4, u.getPergunta());
				pstm.setString(5, u.getResposta());
				pstm.setInt(6, cod);

				pstm.execute();
			} catch (Exception e) {
				System.err.println("editaUsuario: " + e.getMessage());
			}
		}
	}

	public static void excluirCliente(int cod) {
		String query = "UPDATE tbusuario set usuarioAtivo=? WHERE codUsuario=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, 0);
				pstm.setInt(2, cod);
				pstm.execute();
			} catch (Exception e) {
				System.err.println("Excluir cliente: " + e.getMessage());
			}
		}
	}

	public static Usuario getUsuario(String login) {
		String getCod = "SELECT * FROM tbUsuario WHERE loginUsuario = ?"
				+ " AND usuarioAtivo=?";
		Connection con = null;
		ResultSet rs = null;
		Usuario u = new Usuario();
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(getCod);
			pstm.setString(1, login);
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				u.setCod(rs.getInt("codUsuario"));
				u.setLogin(rs.getString("loginUsuario"));
				u.setSenha(rs.getString("senhaUsuario"));
				u.setFuncionario(getFuncionario(rs.getInt("codFuncionario")));
				u.setTipo(rs.getString("tipoUsuario"));
				u.setPergunta(rs.getString("perguntaUsuario"));
				u.setResposta(rs.getString("respostaSecreta"));
			}
		} catch (Exception e) {
			System.err.println("getCodUsuario: " + e.getMessage());
		}
		return u;
	}

	public static Usuario getUsuario(int cod) {
		String query = "SELECT * FROM tbUsuario WHERE codUsuario = ?";
		Connection con = null;
		ResultSet rs = null;
		Usuario u = new Usuario();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				u.setCod(rs.getInt("codUsuario"));
				u.setLogin(rs.getString("loginUsuario"));
				u.setSenha(rs.getString("senhaUsuario"));
				u.setFuncionario(getFuncionario(rs.getInt("codFuncionario")));
				u.setTipo(rs.getString("tipoUsuario"));
				u.setPergunta(rs.getString("perguntaUsuario"));
				u.setResposta(rs.getString("respostaSecreta"));
				//u.setFuncionario(getFuncionarioUsuario(cod));
			}
			return u;
		} catch (Exception e) {
			System.err.println("bugou no getUsuario;\n" + e.getMessage());
		}
		return null;
	}

	public static Funcionario getFuncionarioUsuario(int cod){
		String query = "SELECT * FROM tbFuncionario inner join tbUsuario on tbfuncionario.codFuncionario=tbUsuario.codFuncionario WHERE atividadeFuncionario=? and tbusuario.codFuncionario=?";
		Connection con = null;
		ResultSet rs = null;
		Funcionario f = new Funcionario();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCodFuncionario(rs.getInt("codFuncionario"));
				f.setNomeFuncionario(rs.getString("nomeFuncionario"));
				f.setDataNascFuncionario(rs
						.getString("dataNascimentoFuncionario"));
				f.setRgFuncionario(rs.getString("rgFuncionario"));
				f.setCpfFuncionario(rs.getString("cpfFuncionario"));
				f.setSexoFuncionario(rs.getString("sexoFuncionario"));
				f.setLogradouroFuncionario(rs.getString("logradouro"));
				f.setCepFuncionario(rs.getString("cep"));
				f.setBairroFuncionario(rs.getString("bairro"));
				f.setCidadeFuncionario(rs.getString("cidade"));
				f.setComplementoFuncionario(rs.getString("complemento"));
				f.setEstadoFuncionario(rs.getString("estado"));
				f.setNumFuncionario(rs.getString("numFuncionario"));
				f.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
				f.setEmailFuncionario(rs.getString("emailFuncionario"));
				f.setDataAdmissaoFuncionario(rs.getString("dataAdmissao"));

				String queryTel = "SELECT * FROM tbtelefonefuncionario WHERE (codfuncionario)="
						+ rs.getInt("codFuncionario");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<Telefones> telefones = new ArrayList<Telefones>();
				Telefones tc = new Telefones();
				while (result.next()) {

					tc.setTelefone(result
							.getString("numeroTelefoneFuncionario"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);
			}

			return f;
		} catch (Exception e) {
			System.err.println("bugou no getFuncionarios;\n" + e.getMessage());
		}
		return null;
	}
	
	
	public static ArrayList<Usuario> getUsuarios() {
		String query = "SELECT * FROM tbUsuario WHERE usuarioAtivo=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setCod(rs.getInt("codUsuario"));
				u.setLogin(rs.getString("loginUsuario"));
				u.setSenha(rs.getString("senhaUsuario"));
				u.setFuncionario(getFuncionario(rs.getInt("codFuncionario")));
				u.setTipo(rs.getString("tipoUsuario"));
				u.setPergunta(rs.getString("perguntaUsuario"));
				u.setResposta(rs.getString("respostaSecreta"));
				usuarios.add(u);
			}

			return usuarios;
		} catch (Exception e) {
			System.err.println("bugou no getUsuarios;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Usuario> getUsuarios(String login) {
		String query = "SELECT * FROM tbUsuario WHERE usuarioAtivo=? AND loginUsuario like ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setString(2, "%" + login + "%");
			rs = pstm.executeQuery();
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setCod(rs.getInt("codUsuario"));
				u.setLogin(rs.getString("loginUsuario"));
				u.setSenha(rs.getString("senhaUsuario"));
				u.setFuncionario(getFuncionario(rs.getInt("codFuncionario")));
				u.setTipo(rs.getString("tipoUsuario"));
				u.setPergunta(rs.getString("perguntaUsuario"));
				u.setResposta(rs.getString("respostaSecreta"));
				usuarios.add(u);
			}

			return usuarios;
		} catch (Exception e) {
			System.err.println("bugou no getUsuarios;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Funcionario> getAlgunsFuncionarios() {
		int i = 0;
		ArrayList<Funcionario> ff = new ArrayList<Funcionario>();
		for (Funcionario f : getFuncionarios()) {
			i = 0;
			for (Usuario u : getUsuarios()) {
				if (f.getCodFuncionario() == u.getFuncionario()
						.getCodFuncionario()) {
					i++;
				}
			}
			if (i == 0)
				ff.add(f);
		}
		return ff;
	}

	public static Funcionario getFuncionario(int cod) {
		String query = "SELECT * FROM tbFuncionario WHERE atividadeFuncionario=? AND codFuncionario=?";
		Connection con = null;
		ResultSet rs = null;
		Funcionario f = new Funcionario();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				f.setCodFuncionario(rs.getInt("codFuncionario"));
				f.setNomeFuncionario(rs.getString("nomeFuncionario"));
				f.setDataNascFuncionario(rs
						.getString("dataNascimentoFuncionario"));
				f.setRgFuncionario(rs.getString("rgFuncionario"));
				f.setCpfFuncionario(rs.getString("cpfFuncionario"));
				f.setSexoFuncionario(rs.getString("sexoFuncionario"));
				f.setLogradouroFuncionario(rs.getString("logradouro"));
				f.setCepFuncionario(rs.getString("cep"));
				f.setBairroFuncionario(rs.getString("bairro"));
				f.setCidadeFuncionario(rs.getString("cidade"));
				f.setComplementoFuncionario(rs.getString("complemento"));
				f.setEstadoFuncionario(rs.getString("estado"));
				f.setNumFuncionario(rs.getString("numFuncionario"));
				f.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
				f.setEmailFuncionario(rs.getString("emailFuncionario"));
				f.setDataAdmissaoFuncionario(rs.getString("dataAdmissao"));

				String queryTel = "SELECT * FROM tbtelefonefuncionario WHERE (codfuncionario)="
						+ rs.getInt("codFuncionario");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<Telefones> telefones = new ArrayList<Telefones>();
				Telefones tc = new Telefones();
				while (result.next()) {

					tc.setTelefone(result
							.getString("numeroTelefoneFuncionario"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);
			}

			return f;
		} catch (Exception e) {
			System.err.println("bugou no getFuncionarios;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Funcionario> getFuncionarios() {
		String query = "SELECT * FROM tbfuncionario WHERE atividadeFuncionario=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Funcionario> clientes = new ArrayList<Funcionario>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Funcionario f = new Funcionario();
				f.setCodFuncionario(rs.getInt("codFuncionario"));
				f.setNomeFuncionario(rs.getString("nomeFuncionario"));
				f.setDataNascFuncionario(rs
						.getString("dataNascimentoFuncionario"));
				f.setRgFuncionario(rs.getString("rgFuncionario"));
				f.setCpfFuncionario(rs.getString("cpfFuncionario"));
				f.setSexoFuncionario(rs.getString("sexoFuncionario"));
				f.setLogradouroFuncionario(rs.getString("logradouro"));
				f.setCepFuncionario(rs.getString("cep"));
				f.setBairroFuncionario(rs.getString("bairro"));
				f.setCidadeFuncionario(rs.getString("cidade"));
				f.setComplementoFuncionario(rs.getString("complemento"));
				f.setEstadoFuncionario(rs.getString("estado"));
				f.setNumFuncionario(rs.getString("numFuncionario"));
				f.setSalarioFuncionario(rs.getDouble("salarioFuncionario"));
				f.setEmailFuncionario(rs.getString("emailFuncionario"));
				f.setDataAdmissaoFuncionario(rs.getString("dataAdmissao"));

				String queryTel = "SELECT * FROM tbtelefonefuncionario WHERE (codfuncionario)="
						+ rs.getInt("codFuncionario");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<Telefones> telefones = new ArrayList<Telefones>();
				Telefones tc = new Telefones();
				while (result.next()) {

					tc.setTelefone(result
							.getString("numeroTelefoneFuncionario"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);
				clientes.add(f);
			}

			return clientes;
		} catch (Exception e) {
			System.err.println("bugou no getFuncionarios;\n" + e.getMessage());
		}
		return null;
	}

	public static String getLoginUsuario(String login, int cod) {
		String query = "SELECT loginUsuario FROM tbUsuario WHERE loginUsuario like ?"
				+ " AND codUsuario!=? AND usuarioAtivo=1";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getNome = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1,  login);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getNome = rs.getString("loginUsuario");
			}
			return getNome;
		} catch (Exception e) {
			System.err.println("bugou na duplicidade LoginUsuario;\n"
					+ e.getMessage());
		}
		return null;
	}

}
