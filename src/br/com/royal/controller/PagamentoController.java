package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.PagamentoDao;
import br.com.royal.dao.QuantidadeParcelaDao;
import br.com.royal.model.Pagamento;
import br.com.royal.model.QuantidadeParcela;

public class PagamentoController {

	public static ArrayList<QuantidadeParcela> getQuantidadeParcelas() {
		return QuantidadeParcelaDao.getQuantidadeParcelas() ;
	}
	
	public static int cadastrarPagamento(Pagamento pagamento){
		PagamentoDao pd = new PagamentoDao();
		return pd.cadastrarPagamento(pagamento);
	}
	
}
