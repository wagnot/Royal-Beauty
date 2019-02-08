package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.EntradaCaixaDao;
import br.com.royal.model.Caixa;
import br.com.royal.model.EntradaCaixa;
import br.com.royal.model.MotivoEntradaCaixa;


public class EntradaCaixaController {
	public static ArrayList<EntradaCaixa> getEntradaCaixas() {
		return EntradaCaixaDao.getEntradaCaixas();
	}

	public static ArrayList<EntradaCaixa> getEntradaCaixas(String nome) {
		return EntradaCaixaDao.getEntradaCaixas(nome);
	}

	public static void cadastrar(EntradaCaixa fp) {
		fp.setCodCaixa(new CaixaController().getCaixaAtual().getCod());
		EntradaCaixaDao.cadastrarEntradaCaixa(fp);

//		Caixa caixa = new Caixa();
//		caixa.setDataCaixa(fp.getDataEntradaCaixa().substring(0, 10));
//		caixa.setValorAtual(fp.getValor());
//		new CaixaController().atualizarEntradaCaixa(caixa);
	}
	
	public static ArrayList<EntradaCaixa> getEntradaCaixasPCodCaixa(int codCaixa){
		return EntradaCaixaDao.getEntradaCaixasPCodCaixa(codCaixa);
	}
	
	public static EntradaCaixa getEntradaCaixa(int cod) {
		return EntradaCaixaDao.getEntradaCaixa(cod);
	}

	public static ArrayList<MotivoEntradaCaixa> getMotivos() {
		return EntradaCaixaDao.getMotivoEntradaCaixas();
	}

	public static MotivoEntradaCaixa getMotivo(int cod) {
		return EntradaCaixaDao.getMotivoEntradaCaixa(cod);
	}

	public static MotivoEntradaCaixa getMotivo(String descricao) {
		return EntradaCaixaDao.getMotivoEntradaCaixa(descricao);
	}
}
