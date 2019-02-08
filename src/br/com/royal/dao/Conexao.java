package br.com.royal.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private static Connection conexao;

	public static Connection getConexao() {
		if (conexao != null)
			return conexao;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/bdsalaobeleza";

			conexao = DriverManager.getConnection(url, "root", "");

		} catch (Exception e) {
			System.out.println("getConexao: " + e.getMessage());
			return null;
		}
		return conexao;
	}

//	public static void fechar(Connection conn) {
//		try {
//			conn.close();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

}
