package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.Cliente;
import br.com.royal.model.Produto;
import br.com.royal.model.Servico;
import br.com.royal.model.ServicoComanda;
import br.com.royal.model.Telefones;

public class ComandaDao {

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
		ArrayList<Cliente> funcionarios = new ArrayList<Cliente>();

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
				funcionarios.add(c);
			}
			return funcionarios;
		} catch (Exception e) {
			System.out
					.println("bugou no getClientes(nome);\n" + e.getMessage());
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
