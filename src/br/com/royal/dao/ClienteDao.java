package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import br.com.royal.model.Cliente;
import br.com.royal.model.Telefones;

public class ClienteDao {

	public static void cadastrarCliente(Cliente cliente) {
		String query = "INSERT INTO tbcliente (nomeCliente, dataNascimentoCliente, rgCliente,"
				+ " cpfCliente, sexoCliente, logradouro, numCliente, cep,bairro, cidade,"
				+ " complemento, estado, emailCliente, fotoCliente, atividadeCliente)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getDataNasc());
			pstm.setString(3, cliente.getRg());
			pstm.setString(4, cliente.getCpf());
			pstm.setString(5, cliente.getSexo());
			pstm.setString(6, cliente.getLogradouro());
			pstm.setString(7, cliente.getNumCliente());
			pstm.setString(8, cliente.getCep());
			pstm.setString(9, cliente.getBairro());
			pstm.setString(10, cliente.getCidade());
			pstm.setString(11, cliente.getComplemento());
			pstm.setString(12, cliente.getEstado());
			pstm.setString(13, cliente.getEmail());
			pstm.setString(14, cliente.getFotoCliente());

			pstm.setInt(15, 1);
			pstm.execute();
			cadastrarTelCliente(cliente.getTelefone());
		} catch (Exception e) {
			System.out.println("EROU: " + e.getMessage());
		}
	}

	public static void cadastrarTelCliente(Object lista[]) {
		String idMax = "SELECT * FROM tbCliente WHERE codCliente = (SELECT MAX(codCliente) FROM tbcliente)";
		String query = "INSERT INTO tbtelefonecliente (numeroTelefoneCliente, codCliente) VALUES (?,?)";
		int id = 0;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(idMax);
			rs = pstm.executeQuery();
			while (rs.next()) {
				id = rs.getInt("codCliente");
			}
			PreparedStatement ps = con.prepareStatement(query);
			for (Object c : lista) {
				ps.setString(1, c.toString());
				ps.setInt(2, id);
				ps.execute();
			}
		} catch (Exception e) {
			System.out.println("bugou no cadastro de telefone;\n"
					+ e.getMessage());
		}
	}

	public static void editarCliente(Cliente cliente) {
		String query = "UPDATE tbcliente set nomeCliente=?, dataNascimentoCliente=?, rgCliente=?,"
				+ " cpfCliente=?, sexoCliente=?, logradouro=?, numCliente=?, cep=?,bairro=?, cidade=?,"
				+ " complemento=?, estado=?, emailCliente=?, fotoCliente=? WHERE codCliente=?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getDataNasc());
			pstm.setString(3, cliente.getRg());
			pstm.setString(4, cliente.getCpf());
			pstm.setString(5, cliente.getSexo());
			pstm.setString(6, cliente.getLogradouro());
			pstm.setString(7, cliente.getNumCliente());
			pstm.setString(8, cliente.getCep());
			pstm.setString(9, cliente.getBairro());
			pstm.setString(10, cliente.getCidade());
			pstm.setString(11, cliente.getComplemento());
			pstm.setString(12, cliente.getEstado());
			pstm.setString(13, cliente.getEmail());
			pstm.setString(14, cliente.getFotoCliente());

			pstm.setInt(15, cliente.getCod());

			editarTelCliente(cliente);

			pstm.execute();
		} catch (Exception e) {
			System.out.println("editaCliente: " + e.getMessage());
		}

	}

	public static void editarTelCliente(Cliente cliente) {
		String delete = "DELETE FROM tbtelefonecliente WHERE codCliente=?";
		String query = "INSERT INTO tbtelefonecliente (numeroTelefoneCliente, codCliente) "
				+ "values (?,?)";
		Connection con = null;
		int cod = getCodCliente(cliente.getCpf());
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement ps = con.prepareStatement(delete);
				ps.setInt(1, cod);
				ps.execute();

				ps = con.prepareStatement(query);
				for (Object c : cliente.getTelefone()) {
					ps.setString(1, c.toString());
					ps.setInt(2, cod);
					ps.execute();

				}

			} catch (Exception e) {
				System.out.println("bugou na edição de telefone;\n"
						+ e.getMessage());
			}
		}
	}

	public static void excluirCliente(int cod) {
		String query = "UPDATE tbcliente set atividadeCliente=? WHERE codCliente=?";
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

	public static int getCodCliente(String cpf) {
		String getCod = "SELECT codCliente FROM tbCliente WHERE cpfCliente like ?  and atividadeCliente=?";
		Connection con = null;
		ResultSet rs = null;
		int cod = 0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(getCod);
			pstm.setString(1, cpf);
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod = rs.getInt("codCliente");
			}
		} catch (Exception e) {
			System.out.println("getCodCliente: " + e.getMessage());
		}
		return cod;
	}

	public static ArrayList<Cliente> getClientes() {
		String query = "SELECT * FROM tbCliente WHERE atividadeCliente=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setCod(rs.getInt("codCliente"));
				c.setNome(rs.getString("nomeCliente"));
				c.setDataNasc(rs.getString("dataNascimentoCliente"));
				c.setRg(rs.getString("rgCliente"));
				c.setCpf(rs.getString("cpfCliente"));
				c.setSexo(rs.getString("sexoCliente"));
				c.setLogradouro(rs.getString("logradouro"));
				c.setCep(rs.getString("cep"));
				c.setBairro(rs.getString("bairro"));
				c.setCidade(rs.getString("cidade"));
				c.setComplemento(rs.getString("complemento"));
				c.setEstado(rs.getString("estado"));
				c.setNumCliente(rs.getString("numCliente"));
				c.setEmail(rs.getString("emailCliente"));
				c.setFotoCliente(rs.getString("fotoCliente"));

				String queryTel = "SELECT * FROM tbtelefonecliente WHERE (codCliente)="
						+ rs.getInt("codCliente");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<Telefones> telefones = new ArrayList<Telefones>();
				Telefones tc = new Telefones();
				while (result.next()) {

					tc.setTelefone(result.getString("numeroTelefoneCliente"));
					telefones.add(tc);
				}
				c.setLstTelefones(telefones);
				clientes.add(c);
			}
			Collections.sort(clientes);
			return clientes;
		} catch (Exception e) {
			System.out.println("bugou no getClientes;\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Cliente> getClientes(String nome) {
		String query = "SELECT * FROM tbCliente WHERE (nomeCliente) like ? and atividadeCliente=?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setCod(rs.getInt("codCliente"));
				c.setNome(rs.getString("nomeCliente"));
				c.setDataNasc(rs.getString("dataNascimentoCliente"));
				c.setRg(rs.getString("rgCliente"));
				c.setCpf(rs.getString("cpfCliente"));
				c.setSexo(rs.getString("sexoCliente"));
				c.setLogradouro(rs.getString("logradouro"));
				c.setCep(rs.getString("cep"));
				c.setBairro(rs.getString("bairro"));
				c.setCidade(rs.getString("cidade"));
				c.setComplemento(rs.getString("complemento"));
				c.setEstado(rs.getString("estado"));
				c.setNumCliente(rs.getString("numCliente"));
				c.setEmail(rs.getString("emailCliente"));
				c.setFotoCliente(rs.getString("fotoCliente"));

				String queryTel = "SELECT * FROM tbtelefonecliente WHERE (codCliente)="
						+ rs.getInt("codCliente");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<Telefones> telefones = new ArrayList<Telefones>();
				Telefones tc = new Telefones();
				while (result.next()) {

					tc.setTelefone(result.getString("numeroTelefoneCliente"));
					telefones.add(tc);
				}
				c.setLstTelefones(telefones);

				clientes.add(c);
				Collections.sort(clientes);
			}

			return clientes;
		} catch (Exception e) {
			System.out
					.println("bugou no getClientes(nome);\n" + e.getMessage());
		}
		return null;
	}

	public static String getCpf(String cpf, int cod) {

		String query = "SELECT cpfCliente FROM tbCliente WHERE (cpfCliente) =? and codCliente!=? and atividadeCliente=1";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getCpf = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, cpf);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getCpf = rs.getString("cpfCliente");
			}
			return getCpf;
		} catch (Exception e) {
			System.out.println("bugou na duplicidade CPF;\n" + e.getMessage());
		}
		return null;
	}

	public static String getEmail(String email, int cod) {
		String query = "SELECT emailCliente FROM tbCliente WHERE (emailCliente) =? and codCliente!=? and atividadeCliente=1";
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Conexao.getConexao();
			String getCpf = "";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, email);
			pstm.setInt(2, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				getCpf = rs.getString("emailCliente");
			}
			return getCpf;
		} catch (Exception e) {
			System.out
					.println("bugou na duplicidade Email;\n" + e.getMessage());
		}
		return null;
	}

	public static Cliente getCliente(int cod) {
		String query = "SELECT * FROM tbCliente WHERE (codCliente)=? and atividadeCliente=?";
		String queryTel = "SELECT * FROM tbtelefonecliente WHERE (codCliente)=?";
		Connection con = null;
		ResultSet rs = null;
		Cliente c = new Cliente();
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, cod);
				pstm.setInt(2, 1);
				rs = pstm.executeQuery();
				while (rs.next()) {
					c.setCod(rs.getInt("codCliente"));
					c.setNome(rs.getString("nomeCliente"));
					c.setDataNasc(rs.getString("dataNascimentoCliente"));
					c.setRg(rs.getString("rgCliente"));
					c.setCpf(rs.getString("cpfCliente"));
					c.setSexo(rs.getString("sexoCliente"));
					c.setLogradouro(rs.getString("logradouro"));
					c.setCep(rs.getString("cep"));
					c.setBairro(rs.getString("bairro"));
					c.setCidade(rs.getString("cidade"));
					c.setComplemento(rs.getString("complemento"));
					c.setEstado(rs.getString("estado"));
					c.setNumCliente(rs.getString("numCliente"));
					c.setEmail(rs.getString("emailCliente"));
					c.setFotoCliente(rs.getString("fotoCliente"));
				}
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				ps.setInt(1, cod);
				result = ps.executeQuery();

				ArrayList<Telefones> telefones = new ArrayList<Telefones>();

				while (result.next()) {
					Telefones tc = new Telefones();
					tc.setTelefone(result.getString("numeroTelefoneCliente"));
					telefones.add(tc);
				}
				c.setLstTelefones(telefones);

				return c;
			} catch (Exception e) {
				System.out.println("bugou no getCliente;\n" + e.getMessage());
			}
		}
		return null;
	}
}
