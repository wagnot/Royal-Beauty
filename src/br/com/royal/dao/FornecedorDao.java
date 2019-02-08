package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.royal.controller.BuscaCep;
import br.com.royal.model.Fornecedor;
import br.com.royal.model.TelFornecedor;

public class FornecedorDao {
	public static void cadastrarFornecedor(Fornecedor f) {
		String query = "INSERT INTO tbFornecedor (nomeFornecedor,contatoFornecedor, cnpjFornecedor,cpfFornecedor,atividadeFornecedor)"
				+ " VALUES (?,?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, f.getNomeFornecedor());
			pst.setString(2, f.getContatoFornecedor());
			pst.setString(3, f.getCnpjFornecedor());
			pst.setString(4, f.getCpfFornecedor());
			pst.setInt(5, 1);
			pst.execute();
			cadastrarTelFornecedor(f.getTelefone());

		} catch (Exception e) {
			System.out.println("ERROR NO CADASTRAR" + e.getMessage());
		}
	}

	public static void cadastrarTelFornecedor(Object lista[]) {
		String idMax = "SELECT * FROM tbFornecedor WHERE codFornecedor = (SELECT MAX(codFornecedor) FROM tbFornecedor)";
		String query = "INSERT INTO tbTelefoneFornecedor (numeroTelefoneFornecedor, codFornecedor) VALUES (?,?)";
		int id = 0;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(idMax);
			rs = pstm.executeQuery();
			while (rs.next()) {
				id = rs.getInt("codFornecedor");
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

	public static void editarFornecedor(Fornecedor f, String cpf, String cnpj) {
		String query = "UPDATE tbFornecedor set nomeFornecedor = ?,contatoFornecedor = ?, cnpjFornecedor = ?,"
				+ " cpfFornecedor = ? where codFornecedor = ?";
		Connection con = null;
		int cod = getCodForn(cpf, cnpj);
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, f.getNomeFornecedor());
				pst.setString(2, f.getContatoFornecedor());
				pst.setString(3, f.getCnpjFornecedor());
				pst.setString(4, f.getCpfFornecedor());
				pst.setInt(5, cod);
				editarTelFornecedor(f);
				pst.execute();

			} catch (Exception e) {
				System.out.println("ERROR no editar" + e.getMessage());
			}
		}
	}

	public static void editarTelFornecedor(Fornecedor f) {
		String delete = "DELETE FROM tbTelefoneFornecedor WHERE codFornecedor=?";
		String query = "INSERT INTO tbTelefoneFornecedor (numeroTelefoneFornecedor, codFornecedor) "
				+ "values (?,?)";
		Connection con = null;
		int cod = getCodForn(f.getCpfFornecedor(), f.getCnpjFornecedor());
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
				System.out.println("bugou na edição de telefone;\n"
						+ e.getMessage());
			}
		}
	}

	public void excluirFornecedor(String cpf, String cnpj) {
		String query = "UPDATE tbFornecedor set atividadeFornecedor=? WHERE codFornecedor=?";
		Connection con = null;
		int cod = getCodForn(cpf, cnpj);
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);

				pst.setInt(1, 0);
				pst.setInt(2, cod);
				pst.execute();

			} catch (Exception e) {
				System.out.println("ERRO NO EXCLUIR FORNECEDOR"
						+ e.getMessage());

			}
		}

	}

	public void excluirTelFornecedor(String cpf, String cnpj) {
		String query = "DELETE from tbTelefoneFornecedor where codFornecedor = ?";
		Connection con = null;
		int cod = getCodForn(cpf, cnpj);
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setInt(1, cod);

				pst.execute();

			} catch (Exception e) {
				System.out.println("ERRO NO EXCLUIR Telefone Fornecedor"
						+ e.getMessage());

			}
		}

	}

	public static List<Fornecedor> listar() {
		String query = "SELECT * from tbFornecedor where atividadeFornecedor = ?";

		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			ResultSet rs = pst.executeQuery();
			List<Fornecedor> fornecedor = new ArrayList<Fornecedor>();
			while (rs.next()) {

				Fornecedor f = new Fornecedor();
				f.setCodFornecedor((rs.getInt("codFornecedor")));
				f.setNomeFornecedor(rs.getString("nomeFornecedor"));
				f.setContatoFornecedor(rs.getString("contatoFornecedor"));
				f.setCnpjFornecedor(rs.getString("cnpjFornecedor"));
				f.setCpfFornecedor(rs.getString("cpfFornecedor"));
				String queryTel = "SELECT * FROM tbTelefoneFornecedor WHERE (codFornecedor)="
						+ rs.getInt("codFornecedor");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<TelFornecedor> telefones = new ArrayList<TelFornecedor>();
				TelFornecedor tc = new TelFornecedor();
				while (result.next()) {

					tc.setTelFornecedor(result
							.getString("numeroTelefoneFornecedor"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);
				fornecedor.add(f);
				Collections.sort(fornecedor);
			}
			return fornecedor;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<Fornecedor> listar(String nome) {
		String query = "SELECT * from tbFornecedor WHERE (nomeFornecedor)LIKE ? AND atividadeFornecedor = ?";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Fornecedor> fornecedor = new ArrayList<Fornecedor>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, "%" + nome + "%");
			pstm.setInt(2, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Fornecedor f = new Fornecedor();
				f.setCodFornecedor((rs.getInt("codFornecedor")));
				f.setNomeFornecedor(rs.getString("nomeFornecedor"));
				f.setContatoFornecedor(rs.getString("contatoFornecedor"));
				f.setCnpjFornecedor(rs.getString("cnpjFornecedor"));
				f.setCpfFornecedor(rs.getString("cpfFornecedor"));
				String queryTel = "SELECT * FROM tbTelefoneFornecedor WHERE (codFornecedor)="
						+ rs.getInt("codFornecedor");
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				result = ps.executeQuery();
				ArrayList<TelFornecedor> telefones = new ArrayList<TelFornecedor>();
				TelFornecedor tc = new TelFornecedor();
				while (result.next()) {

					tc.setTelFornecedor(result
							.getString("numeroTelefoneFornecedor"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);
				fornecedor.add(f);
				Collections.sort(fornecedor);
			}
			
			return fornecedor;
		} catch (Exception e) {
			System.out.println("bugou no listarFornecedor(com parametro);\n"
					+ e.getMessage());
		}
		return null;
	}

	public static boolean conferirCpf(String cpf, String cpfTabela) {
		if (!cpf.equals(cpfTabela)) {
			String query = "SELECT * FROM tbFornecedor where cpfFornecedor = ? AND atividadeFornecedor = ?";
			Connection con = null;
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, cpf);
				pst.setInt(2, 1);

				ResultSet rs = pst.executeQuery();
				while (!rs.next()) {
					return true;
				}

			} catch (Exception e) {
				System.out.println("bugou no conferir" + e.getMessage());
			}
			return false;
		} else {
			return true;
		}
	}

	public static boolean conferirCnpj(String cnpj, String cnpjTabela) {
		if (!cnpj.equals(cnpjTabela)) {
			String query = "SELECT * FROM tbFornecedor where cnpjFornecedor = ? AND atividadeFornecedor = ?";
			Connection con = null;
			try {
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, cnpj);
				pst.setInt(2, 1);

				ResultSet rs = pst.executeQuery();
				while (!rs.next()) {
					return true;
				}

			} catch (Exception e) {
				System.out.println("bugou no conferir" + e.getMessage());
			}
			return false;
		} else {
			return true;
		}
	}

	public static int getCodForn(String cpf, String cnpj) {
		String getCod = "SELECT codFornecedor FROM tbFornecedor WHERE cpfFornecedor = ? and cnpjFornecedor = ? and atividadeFornecedor = ?";
		Connection con = null;
		ResultSet rs = null;
		int cod = 0;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(getCod);
			pstm.setString(1, cpf);
			pstm.setString(2, cnpj);
			pstm.setInt(3, 1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod = rs.getInt("codFornecedor");
			}
		} catch (Exception e) {
			System.out.println("getCodFornecedor: " + e.getMessage());
		}
		return cod;
	}

	public static Fornecedor getFornecedor(String cpf, String cnpj) {
		String query = "SELECT * FROM tbFornecedor WHERE codFornecedor=? AND atividadeFornecedor = ?";
		String queryTel = "SELECT * FROM tbTelefoneFornecedor WHERE codFornecedor=?";
		Connection con = null;
		ResultSet rs = null;
		Fornecedor f = new Fornecedor();
		int cod = getCodForn(cpf, cnpj);
		if (cod > 0) {
			try {
				con = Conexao.getConexao();
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, cod);
				pstm.setInt(2, 1);

				rs = pstm.executeQuery();
				while (rs.next()) {
					f.setCodFornecedor(rs.getInt("codFornecedor"));
					f.setNomeFornecedor(rs.getString("nomeFornecedor"));
					f.setContatoFornecedor(rs.getString("contatoFornecedor"));
					f.setCnpjFornecedor(rs.getString("cnpjFornecedor"));
					f.setCpfFornecedor(rs.getString("cpfFornecedor"));

				}
				PreparedStatement ps = con.prepareStatement(queryTel);
				ResultSet result = null;
				ps.setInt(1, cod);
				result = ps.executeQuery();

				ArrayList<TelFornecedor> telefones = new ArrayList<TelFornecedor>();

				while (result.next()) {
					TelFornecedor tc = new TelFornecedor();
					tc.setTelFornecedor(result
							.getString("numeroTelefoneFornecedor"));
					telefones.add(tc);
				}
				f.setLstTelefones(telefones);

				return f;
			} catch (Exception e) {
				System.out
						.println("bugou no getFornecedor;\n" + e.getMessage());
			}
		}
		return null;
	}
}
