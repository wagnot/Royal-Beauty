package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.royal.model.Caixa;
import br.com.royal.model.Cliente;

public class CaixaDao {
	public static void cadastrarCaixa(Caixa caixa) {
		String query = "INSERT INTO tbcaixa (valorFinalCaixa, valorInicialCaixa, dataCaixa, statusCaixa)"
				+ " VALUES (?,?,?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setDouble(1, caixa.getValorAtual());
			pstm.setDouble(2, caixa.getValorInicial());
			pstm.setString(3, caixa.getDataCaixa().substring(0, 10));
			pstm.setBoolean(4, true);

			pstm.execute();
		} catch (Exception e) {
			System.out.println("Quebrou no CadastrarCaixa:\n" + e.getMessage());
		}
	}

	public static void atualizarEntradaCaixa(Caixa caixa) {
		String query = "UPDATE tbcaixa set valorFinalCaixa=? WHERE dataCaixa like ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setDouble(1,
					caixa.getValorAtual()
							+ getCaixaStatus(caixa.getDataCaixa().substring(0, 10))
									.getValorAtual());
			pstm.setString(2, caixa.getDataCaixa().substring(0, 10));

			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void atualizarSaidaCaixa(Caixa caixa) {
		String query = "UPDATE tbcaixa set valorFinalCaixa=? WHERE dataCaixa like ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setDouble(1, getCaixaStatus(caixa.getDataCaixa())
					.getValorAtual() - caixa.getValorAtual());
			pstm.setString(2, caixa.getDataCaixa());

			pstm.execute();
		} catch (Exception e) {
			System.err.println("Quebro no atualizarSaidaCaixa: "
					+ e.getMessage());
		}
	}

	public static void fecharCaixa(String data) {
		String query = "UPDATE tbcaixa set statusCaixa=?, valorFinalCaixa=0 WHERE dataCaixa like ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setBoolean(1, false);
			pstm.setString(2, data);

			pstm.execute();
		} catch (Exception e) {
			System.err.println("Quebro no fecharCaixa: " + e.getMessage());
		}
	}

	public static void ativarCaixa(String data) {
		String query = "UPDATE tbcaixa set statusCaixa=? WHERE dataCaixa like ?";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setBoolean(1, true);
			pstm.setString(2, data);

			pstm.execute();
		} catch (Exception e) {
			System.err.println("Quebro no ativarCaixa: " + e.getMessage());
		}
	}

	public static Caixa getCaixaStatus(String data) {
		String query = "SELECT * FROM tbCaixa WHERE dataCaixa like ? and statusCaixa=1";
		Connection con = null;
		ResultSet rs = null;
		Caixa c = new Caixa();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, data);
			rs = pstm.executeQuery();
			while (rs.next()) {
				c.setCod(rs.getInt("codCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
				c.setValorAtual(rs.getDouble("valorFinalCaixa"));
				c.setValorInicial(rs.getDouble("valorInicialCaixa"));
				c.setDataCaixa(rs.getString("dataCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
			}

			if (c.getDataCaixa() != null)
				return c;
		} catch (Exception e) {
			System.out.println("bugou no getCaixa(data);\n" + e.getMessage());
		}
		return null;
	}

	public static int getCodMax() {
		String query = "SELECT codCaixa FROM tbCaixa WHERE codCaixa=(SELECT MAX(codCaixa) FROM tbCaixa)";
		Connection con = null;
		ResultSet rs = null;
		int cod = 0;

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cod =rs.getInt("codCaixa");
			}

			return cod;
		} catch (Exception e) {
			System.out.println("bugou no getCaixa(data);\n" + e.getMessage());
		}
		return 0;
	}
	
	public static Caixa getUltimoCaixa(){
		return getCaixaCod(getCodMax());
	}

	public static Caixa getCaixaCod(int cod) {
		String query = "SELECT * FROM tbCaixa WHERE codCaixa =?";
		Connection con = null;
		ResultSet rs = null;
		Caixa c = new Caixa();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, cod);
			rs = pstm.executeQuery();
			while (rs.next()) {
				c.setCod(rs.getInt("codCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
				c.setValorAtual(rs.getDouble("valorFinalCaixa"));
				c.setValorInicial(rs.getDouble("valorInicialCaixa"));
				c.setDataCaixa(rs.getString("dataCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
			}

			if (c.getDataCaixa() != null)
				return c;
		} catch (Exception e) {
			System.out.println("bugou no getCaixa(data);\n" + e.getMessage());
		}
		return null;
	}

	public static Caixa getCaixaData(String data) {
		String query = "SELECT * FROM tbCaixa WHERE dataCaixa like ?";
		Connection con = null;
		ResultSet rs = null;
		Caixa c = new Caixa();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, data);
			rs = pstm.executeQuery();
			while (rs.next()) {
				c.setCod(rs.getInt("codCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
				c.setValorAtual(rs.getDouble("valorFinalCaixa"));
				c.setValorInicial(rs.getDouble("valorInicialCaixa"));
				c.setDataCaixa(rs.getString("dataCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
			}

			if (c.getDataCaixa() != null)
				return c;
		} catch (Exception e) {
			System.out.println("bugou no getCaixa(data);\n" + e.getMessage());
		}
		return null;
	}

	public static ArrayList<Caixa> getCaixas() {
		String query = "SELECT * FROM tbCaixa";
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Caixa> caixas = new ArrayList<Caixa>();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Caixa c = new Caixa();
				c.setCod(rs.getInt("codCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
				c.setValorAtual(rs.getDouble("valorFinalCaixa"));
				c.setValorInicial(rs.getDouble("valorInicialCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));

				caixas.add(c);
			}

			return caixas;
		} catch (Exception e) {
			System.out.println("bugou no getCaixas;\n" + e.getMessage());
		}
		return null;
	}

	public static Caixa getCaixaAtual(String data) {
		String query = "SELECT * FROM tbCaixa WHERE dataCaixa like ?";
		Connection con = null;
		ResultSet rs = null;
		Caixa c = new Caixa();

		try {
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, data);
			rs = pstm.executeQuery();
			while (rs.next()) {
				c.setCod(rs.getInt("codCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
				c.setValorAtual(rs.getDouble("valorFinalCaixa"));
				c.setValorInicial(rs.getDouble("valorInicialCaixa"));
				c.setStatus(rs.getBoolean("statusCaixa"));
				c.setDataCaixa(rs.getString("dataCaixa"));
			}

			return c;
		} catch (Exception e) {
			System.out.println("bugou no getCaixaAtivo;\n" + e.getMessage());
		}
		return null;
	}
}
