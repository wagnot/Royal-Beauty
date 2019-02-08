package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.EstoqueDao;
import br.com.royal.model.Estoque;
import br.com.royal.model.Produto;

public class EstoqueController {
	EstoqueDao eDao = new EstoqueDao();

	public void acaoEstoque(Estoque e) {
		if (eDao.conferirEstoque(e)) {
			eDao.editar(e);

		} else {
			eDao.inserirEstoque(e);
		}
	}

	public boolean conferirEstoque(Estoque e) {
		if (eDao.conferirEstoque(e)) {
			return true; // ja existe um estoque
		} else {
			return false;
		}
	}

	public int pegarEstoque(Estoque e) {
		return eDao.conferirQuantidadeProduto(e);
	}
	
	public ArrayList<Produto>listarProdutosEmFalta(){
		return eDao.produtosEmFalta();
	}
	public ArrayList<Produto> listarEstoque(){
		return eDao.listarEstoque();
	}
	
	public ArrayList<Produto>pesquisarProdutosEmfalta(String codBarras){
		return eDao.pesquisarProdutosEmFalta(codBarras);
	}
	
	public ArrayList<Produto>pesquisarEstoqueGeral(String codBarras){
		return eDao.pesquisarEstoqueGeral(codBarras);
	}

}