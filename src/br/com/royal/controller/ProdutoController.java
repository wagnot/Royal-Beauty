package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.EntradaProdutoDao;
import br.com.royal.dao.ProdutoDao;
import br.com.royal.model.Produto;

public class ProdutoController {

	private ProdutoDao pDao = new ProdutoDao();
	private EntradaProdutoDao epDao = new EntradaProdutoDao();

	/**
	 * Cadastro de um produto no banco de dados (Bancodedados list<Produto>
	 * Singleton)
	 * 
	 * @param produto
	 *            preenchido
	 * @return true = ok ou false = erro
	 */

	public boolean cadastrar(Produto produto) {
		if (pDao.conferirNomeDescricao(produto)) {

			pDao.inserirProduto(produto);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean conferirBarras(String codigoBarras){
		if (pDao.conferirBarras(codigoBarras)){
			return true;
		}else{
			return false;
		}
	}
		public Produto codigoBarras(Produto codigoBarras){
			if (codigoBarras.equals("")){
				return null;
			}else{
				return pDao.conferirCodigoBarras(codigoBarras);
			}
		}
		
		public Produto codigoBarras(String codigoBarras){
			if (codigoBarras.equals("")){
				return null;
			}else{
				return pDao.conferirCodigoBarras(codigoBarras);
			}
		}

	/**
	 * Lista todos os produtos cadastrados
	 * 
	 * @return List<Produto>
	 */
	public List<Produto> listar() {
		return pDao.listar();
	}

	public boolean jaExiste(Produto produto) {
		if (produto == null) {
			return false;
		}
		return pDao.findById(produto.getId()) != null;

	}

	public boolean remover(Produto p) {

		return pDao.excluirProduto(p);

	}

	public Produto find(String codigo) {
		return pDao.findById(Integer.parseInt(codigo));

	}

	public List<Produto>achouBarras (String barras){
		return pDao.findBarras(barras);
	}
	
	public List<Produto> achou(String nome) {
		return pDao.find(nome);

	}

	public boolean atualizar(Produto p, String a, String b) {
		if (a.equals(p.getNomeProduto()) && b.equals(p.getDescricaoProduto())) {
			pDao.editar(p, a, b);
			// epDao.editar(ep,a,b);

			return true;
		}
		if (pDao.conferirNomeDescricao(p)) {
			pDao.editar(p, a, b);
			// epDao.editar(ep,a,b);
			return true;
		} else {

			return false;
		}

	}
	public Produto findById(long codigo){
		return pDao.findById(codigo);
	}

	// public boolean preencher (JComboBox<String> j ){
	// pDao.preencherCombo(j);
	// return true;

	// }

}
