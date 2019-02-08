 package br.com.royal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.royal.model.Estoque;
import br.com.royal.model.Produto;

public class EstoqueDao {
	
	public void inserirEstoque(Estoque e) {
		String query = "INSERT INTO tbEstoque (codProduto,quantidadeEstoqueProduto) VALUES(?,?)";
		Connection con = null;
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1,e.getCodProduto());
			pst.setInt(2,e.getQuantidadeEstoqueProduto());
			pst.execute();
		} catch (Exception ex) {
			System.out.println("Quebrou no inserir estoque" + ex.getMessage());
		}
	}
	
	public boolean conferirEstoque (Estoque e){
		String query = "SELECT * FROM tbEstoque inner join tbProduto on tbEstoque.codProduto = tbProduto.codProduto where tbEstoque.codProduto =? and atividadeProduto =1";
		Connection con = null;
		try{
			con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setLong(1, e.getCodProduto());
			ResultSet rs = pstm.executeQuery();
		
			while (rs.next()) {
				return true; // se retornar true, eu chamo o update e acrescento no estoque
			}
			
			
		}catch(Exception ex){
			System.out.println("Quebrou no conferir estoque "+ ex.getMessage());
			
		}
		return false; // se retornar false, eu chamo o insert, para inserir esse produto no estoque
	}
	
	
	public boolean editar(Estoque e) {
		String query = "UPDATE tbEstoque set quantidadeEstoqueProduto=?  WHERE codProduto = ?";
		Connection con = null;
		
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, e.getQuantidadeEstoqueProduto());
			pst.setLong(2, e.getCodProduto());
			pst.execute();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}

	}
	
	public boolean retirarEstoque(Estoque e, int estoque){
		String query = "UPDATE tbEstoque set quantidadeEstoqueProduto=?  WHERE codProduto = ?";
		Connection con = null;
		
		try {
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, estoque);
			pst.setLong(2, e.getCodProduto());
			pst.execute();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		
		
		
		
	}
	
	
	
	// se for insert, a quantidade do produto vira a quantidade estoque
	// se for update, eu uso esse metodo + quantidadeEntrada, ou, quantidadeSaida - esse metodo
	
	public int conferirQuantidadeProduto(Estoque e){ 
		String query = "SELECT quantidadeEstoqueProduto from tbEstoque inner join tbProduto on tbEstoque.codProduto = tbProduto.codProduto where tbEstoque.codProduto = ? and atividadeProduto = 1";
		Connection con = null;
		int quantidade=0;
		try{
			con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1, e.getCodProduto());
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				//e.setCodFabricante(rs.getInt("codFabricante"));
				quantidade =  rs.getInt("quantidadeEstoqueProduto");
			}
		}catch(Exception ex){
			System.out.println("Quebrou no passar int"+ex.getMessage());
			
			
		}
		return quantidade;
	}
	
	
		public static ArrayList<Produto> produtosEmFalta(){
			String query = "SELECT tbEstoque.codProduto,nomeProduto,descricaoProduto,codigoDeBarras,custoProduto,lucroProduto,quantidadeEstoqueProduto FROM tbEstoque inner join tbProduto on tbEstoque.codProduto = tbProduto.codProduto where quantidadeEstoqueProduto < quantidadeMinima and atividadeProduto = 1";
			Connection con = null;
			try{
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				
				ResultSet rs = pst.executeQuery();
				ArrayList<Produto> produtos = new ArrayList<Produto>();
				while (rs.next()) {

					Produto p = new Produto();
					p.setId(rs.getInt("codProduto"));
					p.setNomeProduto(rs.getString("nomeProduto"));
					p.setDescricaoProduto(rs.getString("descricaoProduto"));
					p.setQuantidadeProdutoEstoque(rs.getInt("quantidadeEstoqueProduto"));				
					p.setCodBarras(rs.getString("codigoDeBarras"));					
					p.setCustoProduto(rs.getDouble("custoProduto"));
					p.setLucroProduto(rs.getDouble("lucroProduto"));
					produtos.add(p);
					
				}
			
				return  produtos;
				
				
			}catch(Exception e){
				System.out.println("QUEBROU NO LISTAR PRODUTOS EM FALTA "+e.getMessage());
				return null;
				
			}
			
		}
		
		public static ArrayList<Produto> listarEstoque(){
			String query = "SELECT tbEstoque.codProduto,nomeProduto,descricaoProduto,codigoDeBarras,"
			+ "custoProduto,lucroProduto,quantidadeEstoqueProduto FROM tbEstoque inner join tbProduto on"
			+ " tbEstoque.codProduto = tbProduto.codProduto where atividadeProduto = 1";
			Connection con = null;
			try{
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				
				ResultSet rs = pst.executeQuery();
				ArrayList<Produto> produtos = new ArrayList<Produto>();
				while (rs.next()) {

					Produto p = new Produto();
					p.setId(rs.getInt("codProduto"));
					p.setNomeProduto(rs.getString("nomeProduto"));
					p.setDescricaoProduto(rs.getString("descricaoProduto"));
					p.setQuantidadeProdutoEstoque(rs.getInt("quantidadeEstoqueProduto"));				
					p.setCodBarras(rs.getString("codigoDeBarras"));					
					p.setCustoProduto(rs.getDouble("custoProduto"));
					p.setLucroProduto(rs.getDouble("lucroProduto"));
					produtos.add(p);
					
				}
			
				return  produtos;
				
				
			}catch(Exception e){
				System.out.println("QUEBROU NO LISTAR PRODUTOS EM FALTA "+e.getMessage());
				return null;
				
			}
			
		}
		
		
		
		
		
		
		
		
		public static ArrayList<Produto> pesquisarProdutosEmFalta(String codBarras){
			String query = "SELECT tbProduto.codProduto,nomeProduto,descricaoProduto,codigoDeBarras,custoProduto"
					+ ",lucroProduto,quantidadeEstoqueProduto FROM tbEstoque inner join tbProduto on "
					+ "tbEstoque.codProduto = tbProduto.codProduto where quantidadeEstoqueProduto < "
					+ "quantidadeMinima and atividadeProduto = 1 and (codigoDeBarras LIKE ? or"
					+ " nomeProduto LIKE ?)";
			Connection con = null;
			try{
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, "%"+codBarras+"%");
				pst.setString(2, "%"+codBarras+"%");
				ResultSet rs = pst.executeQuery();
				ArrayList<Produto> produtos = new ArrayList<Produto>();
				while (rs.next()) {

					Produto p = new Produto();
					p.setId(rs.getInt("codProduto"));
					p.setNomeProduto(rs.getString("nomeProduto"));
					p.setDescricaoProduto(rs.getString("descricaoProduto"));
					p.setQuantidadeProdutoEstoque(rs.getInt("quantidadeEstoqueProduto"));				
					p.setCodBarras(rs.getString("codigoDeBarras"));					
					p.setCustoProduto(rs.getDouble("custoProduto"));
					p.setLucroProduto(rs.getDouble("lucroProduto"));
					produtos.add(p);
					
				}
			
				return  produtos;
				
				
			}catch(Exception e){
				System.out.println("QUEBROU NO LISTAR (PESQUISAR) PRODUTOS EM FALTA "+e.getMessage());
				return null;
				
			}
			
		}
		
		
		public static ArrayList<Produto> pesquisarEstoqueGeral(String codBarras){
			String query = "SELECT tbProduto.codProduto,nomeProduto,descricaoProduto,codigoDeBarras,custoProduto"
					+ ",lucroProduto,quantidadeEstoqueProduto FROM tbEstoque inner join tbProduto on "
					+ "tbEstoque.codProduto = tbProduto.codProduto where  "
					+ "  atividadeProduto = 1 and (codigoDeBarras LIKE ? or"
					+ " nomeProduto LIKE ?)";
			Connection con = null;
			try{
				con = Conexao.getConexao();
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, "%"+codBarras+"%");
				pst.setString(2, "%"+codBarras+"%");
				ResultSet rs = pst.executeQuery();
				ArrayList<Produto> produtos = new ArrayList<Produto>();
				while (rs.next()) {

					Produto p = new Produto();
					p.setId(rs.getInt("codProduto"));
					p.setNomeProduto(rs.getString("nomeProduto"));
					p.setDescricaoProduto(rs.getString("descricaoProduto"));
					p.setQuantidadeProdutoEstoque(rs.getInt("quantidadeEstoqueProduto"));				
					p.setCodBarras(rs.getString("codigoDeBarras"));					
					p.setCustoProduto(rs.getDouble("custoProduto"));
					p.setLucroProduto(rs.getDouble("lucroProduto"));
					produtos.add(p);
					
				}
			
				return  produtos;
				
				
			}catch(Exception e){
				System.out.println("QUEBROU NO LISTAR (PESQUISAR) PRODUTOS EM GERAL "+e.getMessage());
				return null;
				
			}
			
		}
	
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	

}
