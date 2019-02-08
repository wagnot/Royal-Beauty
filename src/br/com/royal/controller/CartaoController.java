package br.com.royal.controller;

import br.com.royal.dao.CartaoDao;
import br.com.royal.model.Cartao;

public class CartaoController {
	private CartaoDao cartaoDao = new CartaoDao();

	public void cadastrarCartao(Cartao c) {
		cartaoDao.cadastrarCartao(c);
	}
}
