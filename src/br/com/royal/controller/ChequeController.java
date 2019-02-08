package br.com.royal.controller;

import br.com.royal.dao.ChequeDao;
import br.com.royal.model.Cheque;

public class ChequeController {
	private ChequeDao chequeDao = new ChequeDao();
	public void cadastrarCheque(Cheque c) {
		chequeDao.cadastrarCheque(c);
	}

}
