package br.com.royal.controller;

import br.com.royal.dao.ParcelaDao;
import br.com.royal.model.Parcela;

public class ParcelaController {
	private ParcelaDao parcelaDao = new ParcelaDao();
	public int cadastrarParcela(Parcela parcela){
		return parcelaDao.cadastrarParcela(parcela);
	}
}
