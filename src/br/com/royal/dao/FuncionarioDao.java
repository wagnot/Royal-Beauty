package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import br.com.royal.model.Funcionario;
import br.com.royal.model.Servico;
import br.com.royal.model.ServicoFuncionario;
import br.com.royal.model.Telefones;
import br.com.royal.model.Usuario;

public class FuncionarioDao {
	public static void cadastrarFuncionario(Funcionario f) {
		String query = "INSERT INTO tbfuncionario (nomeFuncionario, rgFuncionario,"
				+ "cpfFuncionario,sexoFuncionario,dataNascimentoFuncionario,"
				+ "dataAdmissao, salarioFuncionario, logradouro, cep, bairro,"
				+ "cidade,complemento,estado,"
				+ "numFuncionario, emailFuncionario, atividadeFuncionario) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
		
			pstm.setString(1, f.getNomeFuncionario());
			pstm.setString(2, f.getRgFuncionario());
			pstm.setString(3, f.getCpfFuncionario());
			pstm.setString(4, f.getSexoFuncionario());
			pstm.setString(5, f.getDataNascFuncionario());
			pstm.setString(6, f.getDataAdmissaoFuncionario());
			pstm.setDouble(7, f.getSalarioFuncionario());
			pstm.setString(8, f.getLogradouroFuncionario());
			pstm.setString(9, f.getCepFuncionario());
			pstm.setString(10, f.getBairroFuncionario());
			pstm.setString(11, f.getCidadeFuncionario());
			pstm.setString(12, f.getComplementoFuncionario());
			pstm.setString(13, f.getEstadoFuncionario());
			pstm.setString(14, f.getNumFuncionario());
			pstm.setString(15, f.getEmailFuncionario());
			pstm.setInt(16, 1);
			
			pstm.execute();
			cadastrarTelFuncionario(f.getTelefone());
			cadastrarServicoFuncionario(f.getLstServicoFuncionario());
		} catch (Exception e) {
			System.out.println("bugou no cadastro de Funcionario: "
					+ e.getMessage());
		}
	}

	private static void cadastrarTelFuncionario(Object lista[]) {
		String idMax = "SELECT * FROM tbFuncionario WHERE codFuncionario = (SELECT MAX(codFuncionario) FROM tbFuncionario)";
		String query = "INSERT INTO tbtelefonefuncionario (numeroTelefoneFuncionario, codFuncionario) VALUES (?,?)";
		int id = 0;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(idMax);
			rs = pstm.executeQuery();
			while (rs.next()) {
				id = rs.getInt("codFuncionario");
			}
			PreparedStatement ps = con.prepareStatement(query);
			for (Object c : lista) {
				ps.setString(1, c.toString());
				ps.setInt(2, id);
				ps.execute();
			}
		} catch (Exception e) {
			System.out.println("bugou no cadastro de telefoneFuncionario;\n"
					+ e.getMessage());
		}
	}

	public static void cadastrarServicoFuncionario(
			ArrayList<ServicoFuncionario> serFunc) {
		String query = "INSERT INTO tbservicofuncionario (codServico, codFuncionario) VALUES (?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			for (ServicoFuncionario sf : serFunc) {
				pstm.setInt(1, getCodServico(sf.getServico()));
				pstm.setInt(2, getCodFuncionario(sf.getFuncionario()));

				pstm.execute();
			}
		} catch (Exception e) {
			System.out.println("bugou no cadastro de ServicoFuncionario: "
					+ e.getMessage());
		}
	}

	public static void editarFuncionario(Funcionario f, int cod) {
		String query = "UPDATE tbfuncionario set nomeFuncionario=?, rgFuncionario=?,"
				+ "cpfFuncionario=?,sexoFuncionario=?,dataNascimentoFuncionario=?,"
				+ "dataAdmissao=?, salarioFuncionario=?, logradouro=?, numFuncionario=?,cep=?, bairro=?,"
				+ "cidade=?,complemento=?,estado=?,emailFuncionario=?"
				+ " WHERE codFuncionario=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setString(1, f.getNomeFuncionario());
				pstm.setString(2, f.getRgFuncionario());
				pstm.setString(3, f.getCpfFuncionario());
				pstm.setString(4, f.getSexoFuncionario());
				pstm.setString(5, f.getDataNascFuncionario());
				pstm.setString(6, f.getDataAdmissaoFuncionario());
				pstm.setDouble(7, f.getSalarioFuncionario());
				pstm.setString(8, f.getLogradouroFuncionario());
				pstm.setString(9, f.getNumFuncionario());
				pstm.setString(10, f.getCepFuncionario());
				pstm.setString(11, f.getBairroFuncionario());
				pstm.setString(12, f.getCidadeFuncionario());
				pstm.setString(13, f.getComplementoFuncionario());
				pstm.setString(14, f.getEstadoFuncionario());
				pstm.setString(15, f.getEmailFuncionario());

				pstm.setInt(16, cod);

				editarTelFuncionario(f);
				editarServicoFuncionario(f);

				pstm.execute();
			} catch (Exception e) {
				System.out.println("editaFuncionario: " + e.getMessage());
			}
		}
	}

	private static void editarTelFuncionario(Funcionario f) {
		String delete = "DELETE FROM tbtelefonefuncionario WHERE codFuncionario=?";
		String query = "INSERT INTO  tbtelefonefuncionario (numeroTelefoneFuncionario, codFuncionario) "
				+ "values (?,?)";
		Connection con = null;
		int cod = getCodFuncionario(f.getCpfFuncionario());
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement ps = con.prepareStatement(delete);
				ps.setInt(1, cod);
				ps.execute();

				ps = con.prepareStatement(query);
				for (Object c : f.getTelefone()) {
					ps.setString(1, c.toString());
					ps.setInt(2, cod);
					ps.execute();

				}

			} catch (Exception e) {
				System.out.println("bugou na edição de telefone Funcionario;\n"
						+ e.getMessage());
			}
		}

	}

	private static void editarServicoFuncionario(Funcionario f) {
		String delete = "DELETE FROM tbservicofuncionario WHERE codFuncionario=?";
		String query = "INSERT INTO tbservicofuncionario (codServico, codFuncionario) "
				+ "values (?,?)";
		Connection con = null;
		int cod = getCodFuncionario(f.getCpfFuncionario());
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement ps = con.prepareStatement(delete);
				ps.setInt(1, cod);
				ps.execute();

				ps = con.prepareStatement(query);
				for (ServicoFuncionario sf : f.getLstServicoFuncionario()) {
					ps.setInt(1, getCodServico(sf.getServico()));
					ps.setInt(2, cod);
					ps.execute();

				}

			} catch (Exception e) {
				System.out.println("bugou na edição de telefone Funcionario;\n"
						+ e.getMessage());
			}
		}

	}

	public static void excluirFuncionario(int cod) {
		String query = "UPDATE tbFuncionario set atividadeFuncionario=? WHERE codFuncionario=?";
		Connection con = null;
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, 0);
				pstm.setInt(2, cod);
				pstm.execute();
			} catch (Exception e) {
				System.out.println("EROU: " + e.getMessage());
			}
		}
	}

	private static int getCodServico(String nomeServico) {
		String getCod = "SELECT codServico FROM tbServico WHERE nomeServico = ?  and atividadeServico=?";
		Connection con = null;
		ResultSet rs = null;
		int cod = 0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(getCod);
			pstm.setString(1, nomeServico);
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod = rs.getInt("codServico");
			}
		} catch (Exception e) {
			System.out.println("getCodServico(String): " + e.getMessage());
		}
		return cod;
	}

	public static int getCodFuncionario(String cpf) {
		String getCod = "SELECT codFuncionario FROM tbFuncionario WHERE cpfFuncionario like ? and atividadeFuncionario=?";
		Connection con = null;
		ResultSet rs = null;
		int cod = 0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(getCod);
			pstm.setString(1, "%"+cpf+"%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod = rs.getInt("codFuncionario");
			}
		} catch (Exception e) {
			System.out.println("getCodFuncionario: " + e.getMessage());
		}
		return cod;
	}

	public static ArrayList<Funcionario> getFuncionarios() {
		String query = "SELECT * FROM tbFuncionario WHERE atividadeFuncionario=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

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

				String queryTel = "SELECT * FROM tbtelefonefuncionario WHERE (codfuncionario)=?";
				PreparedStatement ps = con.prepareStatement(queryTel);
				ps.setInt(1, rs.getInt("codFuncionario"));

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
				funcionarios.add(f);

				String querySerFunc = "SELECT * From tbServicoFuncionario WHERE codFuncionario=?";
				ps = con.prepareStatement(querySerFunc);
				ps.setInt(1, f.getCodFuncionario());
				result = ps.executeQuery();
				ArrayList<ServicoFuncionario> serfuncs = new ArrayList<ServicoFuncionario>();
				while (result.next()) {
					ServicoFuncionario sf = new ServicoFuncionario();
					sf.setServico(getServico(result.getInt("codServico"))
							.getNomeServico());
					serfuncs.add(sf);
				}
				f.setLstServicoFuncionario(serfuncs);
			}
			Collections.sort(funcionarios);
			return funcionarios;
		} catch (Exception e) {
			System.out.println("bugou no getFuncionarios;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Funcionario> getFuncionarios(String nome) {
		String query = "SELECT * FROM tbFuncionario WHERE (nomeFuncionario) like ? AND atividadeFuncionario=1 ";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%"+nome+"%");
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

				String queryTel = "SELECT * FROM tbtelefonefuncionario WHERE (codfuncionario)=?";
				PreparedStatement ps = con.prepareStatement(queryTel);
				ps.setInt(1, rs.getInt("codFuncionario"));
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
				funcionarios.add(f);

				String querySerFunc = "SELECT * From tbServicoFuncionario WHERE codFuncionario=?";
				ps = con.prepareStatement(querySerFunc);
				ps.setInt(1, f.getCodFuncionario());
				result = ps.executeQuery();
				ArrayList<ServicoFuncionario> serfuncs = new ArrayList<ServicoFuncionario>();
				while (result.next()) {
					ServicoFuncionario sf = new ServicoFuncionario();
					sf.setServico(getServico(result.getInt("codServico"))
							.getNomeServico());
					serfuncs.add(sf);
				}
				f.setLstServicoFuncionario(serfuncs);
			}
			Collections.sort(funcionarios);
			return funcionarios;
		} catch (Exception e) {
			System.out.println("bugou no getFuncionarios(String);\n" + e.getMessage());
		}
		return null;
	}

	public static Funcionario getFuncionario(int cod) {
		String query = "SELECT * FROM tbFuncionario WHERE atividadeFuncionario=? AND (codFuncionario) = ? ";
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

				String queryTel = "SELECT * FROM tbtelefonefuncionario WHERE (codfuncionario)=?";
				PreparedStatement ps = con.prepareStatement(queryTel);
				ps.setInt(1, rs.getInt("codFuncionario"));
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<Telefones> telefones = new ArrayList<Telefones>();

				while (result.next()) {
					Telefones tc = new Telefones();
					tc.setTelefone(result
							.getString("numeroTelefoneFuncionario"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);

				String querySerFunc = "SELECT * From tbServicoFuncionario WHERE codFuncionario=?";
				ps = con.prepareStatement(querySerFunc);
				ps.setInt(1, f.getCodFuncionario());
				result = ps.executeQuery();
				ArrayList<ServicoFuncionario> serfuncs = new ArrayList<ServicoFuncionario>();
				while (result.next()) {
					ServicoFuncionario sf = new ServicoFuncionario();
					sf.setServico(getServico(result.getInt("codServico"))
							.getNomeServico());
					serfuncs.add(sf);
				}
				f.setLstServicoFuncionario(serfuncs);
			}

			return f;
		} catch (Exception e) {
			System.out.println("bugou no getFuncionario;\n" + e.getMessage());
		}
		return null;
	}

	private static Servico getServico(int cod) {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=? AND codServico=?";
		Connection con = null;
		ResultSet rs = null;
		Servico s = new Servico();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				s.setCodServico(rs.getInt("codServico"));
				s.setNomeServico(rs.getString("nomeServico"));
				s.setDescricaoServico(rs.getString("descricaoServico"));
				s.setValorServico(rs.getDouble("valorServico"));
			}

			return s;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;

	}

	public static String getCpf(String cpf, int cod) {
		String query = "SELECT cpfFuncionario FROM tbFuncionario WHERE (cpfFuncionario) = ? "
				+ "and atividadeFuncionario=? AND codFuncionario != ?";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getCpf = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, cpf);
			pstm.setInt(2, 1);
			pstm.setInt(3, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getCpf = rs.getString("cpfFuncionario");
			}
			return getCpf;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade CPF;\n" + e.getMessage());
		}
		return null;
	}

	public static String getEmail(String email, int cod) {
		String query = "SELECT emailFuncionario FROM tbFuncionario WHERE (emailFuncionario) = ? "
				+ "and atividadeFuncionario=? AND codFuncionario != ?";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getEmail = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, email);
			pstm.setInt(2, 1);
			pstm.setInt(3, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getEmail = rs.getString("emailFuncionario");
			}
			return getEmail;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade Email;\n" + e.getMessage());
		}
		return null;
	}

	public static String getPis(String pis) {
		String query = "SELECT pisFuncionario FROM tbFuncionario WHERE (pisFuncionario) = ? and atividadeFuncionario=?";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getCpf = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, pis);
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getCpf = rs.getString("pisFuncionario");
			}
			return getCpf;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade PIS;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Servico> getServicos() {
		String query = "SELECT * FROM tbServico WHERE atividadeServico=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Servico> servicos = new ArrayList<Servico>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Servico s = new Servico();
				s.setCodServico(rs.getInt("codServico"));
				s.setNomeServico(rs.getString("nomeServico"));
				s.setDescricaoServico(rs.getString("descricaoServico"));
				s.setValorServico(rs.getDouble("valorServico"));

				servicos.add(s);
			}

			return servicos;
		} catch (Exception e) {
			System.out.println("bugou no getServicos;\n" + e.getMessage());
		}
		return null;
	}
	
	public static Usuario verificaUsuarioExistente(int cod){
		String query = "SELECT * FROM tbfuncionario "
				+ "LEFT OUTER JOIN tbusuario on tbfuncionario.codFuncionario = tbusuario.codFuncionario "
				+ "WHERE tbfuncionario.codFuncionario=? and tbusuario.usuarioAtivo=1";
		Connection con = null;
		ResultSet rs = null;
		Usuario us = new Usuario();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				us.setCod(rs.getInt("codUsuario"));
				us.setLogin(rs.getString("loginUsuario"));
			}
			
			if(us.getLogin()!=null)
				return us;
		} catch (Exception e) {
			System.out.println("bugou no getUsuarioExistente;\n" + e.getMessage());
		}
		return null;
	}
}
