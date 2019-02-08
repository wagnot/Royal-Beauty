package br.com.royal.controller;

import br.com.royal.dao.ComandaProdutoDao;
import br.com.royal.model.ComandaProduto;
import br.com.royal.model.Produto;

public class ComandaProdutoController {

	public static Produto getProduto(String codigoBarras) {
		return ComandaProdutoDao.getProduto(codigoBarras);
	}
	
	public static int cadastarComandaProduto(ComandaProduto comandaProduto){
		return ComandaProdutoDao.cadastrarComandaProduto(comandaProduto);
	}
}
