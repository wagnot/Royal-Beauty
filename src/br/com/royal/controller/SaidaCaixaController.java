package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.SaidaCaixaDao;
import br.com.royal.model.Caixa;
import br.com.royal.model.MotivoSaidaCaixa;
import br.com.royal.model.SaidaCaixa;

public class SaidaCaixaController {
	public static ArrayList<SaidaCaixa> getSaidaCaixas() {
		return SaidaCaixaDao.getSaidaCaixas();
	}
	
	public static ArrayList<SaidaCaixa> getSaidaCaixas(String nome) {
		return SaidaCaixaDao.getSaidaCaixas(nome);
	}

	public static ArrayList<SaidaCaixa> getSaidaCaixasPCodCaixa (int codCaixa){
		return SaidaCaixaDao.getSaidaCaixasPCodCaixa(codCaixa);
	}
	
	public static void cadastrar(SaidaCaixa fp) {
		fp.setCodCaixa(new CaixaController().getCaixaAtual().getCod());
		SaidaCaixaDao.cadastrarSaidaCaixa(fp);
		
		Caixa caixa = new Caixa();
		caixa.setDataCaixa(fp.getDataSaidaCaixa().substring(0, 10));
		caixa.setValorAtual(fp.getValor());
		new CaixaController().atualizarSaidaCaixa(caixa);
	}

	public static SaidaCaixa getSaidaCaixa(int codFormaDePagamento) {
		return SaidaCaixaDao.getSaidaCaixa(codFormaDePagamento);
	}
	
	public static ArrayList<MotivoSaidaCaixa> getMotivos(){
		return SaidaCaixaDao.getMotivoSaidaCaixas();
	}
	
	public static MotivoSaidaCaixa getMotivo(int cod){
		return SaidaCaixaDao.getMotivoSaidaCaixa(cod);
	}
	
	public static MotivoSaidaCaixa getMotivo(String descricao){
		return SaidaCaixaDao.getMotivoSaidaCaixa(descricao);
	}
}
