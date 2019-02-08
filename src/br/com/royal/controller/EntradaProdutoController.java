package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.EntradaProdutoDao;
import br.com.royal.model.EntradaProduto;
import br.com.royal.model.Produto;

public class EntradaProdutoController {
	private EntradaProdutoDao epDao = new EntradaProdutoDao();

	public boolean cadastrar(EntradaProduto ep) {

		if (ep.getQuantidadeEntradaProduto() < 1)
			return false;
		else
			epDao.inserir(ep);
		return true;
	}

	public List<EntradaProduto> listar() {
		return epDao.listar();
	}

	public boolean jaExiste(EntradaProduto ep) {
		if (ep == null) {
			return false;
		}
		return epDao.findById(ep.getIdEntradaProduto()) != null;

	}

	public boolean remover(int codigo) {

		return epDao.excluirEntradaProduto(codigo);

	}

	public EntradaProduto find(int codigo) {
		return epDao.findById(codigo);

	}

	public boolean atualizar(EntradaProduto ep, int codigo) {
		epDao.editar(ep, codigo);
		return true;

	}

	public List<EntradaProduto> achou(String lote) {
		return epDao.find(lote);

	}

}
