package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.SaidaProdutoDao;
import br.com.royal.model.EntradaProduto;
import br.com.royal.model.SaidaProduto;


public class SaidaProdutoController {
	private SaidaProdutoDao spDao = new SaidaProdutoDao();

	public boolean cadastrar(SaidaProduto sp) {

		if (sp.getQuantidadeSaidaProduto() < 1)
			return false;
		else
			spDao.inserir(sp);
		return true;
	}

	public List<SaidaProduto> listar() {
		return spDao.listar();
	}

	
	public SaidaProduto find(int codigo) {
		return spDao.findById(codigo);

	}
	public List<SaidaProduto> achou(String dataSaidaProduto) {
		return spDao.find(dataSaidaProduto);

	}
	

	

	public boolean atualizar(SaidaProduto ep, int codigo) {
		spDao.editar(ep, codigo);
		return true;

	}

//	public List<SaidaProduto> achou(String data) {
//		return epDao.find(lote);

//	}
	
	

}
