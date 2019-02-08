package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.MotivoEntradaCaixaDao;
import br.com.royal.model.MotivoEntradaCaixa;


public class MotivoEntradaCaixaController {
	

	public static ArrayList<MotivoEntradaCaixa> getMotivoEntradaCaixas() {
		return MotivoEntradaCaixaDao.getMotivoEntradaCaixas();
	}
	
	public static ArrayList<MotivoEntradaCaixa> getMotivoEntradaCaixas(String nome) {
		return MotivoEntradaCaixaDao.getMotivoEntradaCaixas(nome);
	}

	public static boolean conferirDuplicidadeDescricao(String descricao, int cod){
		if(descricao.equals(MotivoEntradaCaixaDao.getDescricaoMotivoEntradaCaixa(descricao, cod)))
			return true;
		return false;
	}

	public static void cadastrar(MotivoEntradaCaixa fp) {
		MotivoEntradaCaixaDao.cadastrarMotivoEntradaCaixa(fp);
	}

	public static void editar(MotivoEntradaCaixa fp, int cod) {
		MotivoEntradaCaixaDao.editarMotivoEntradaCaixa(fp, cod);
	}

	public static MotivoEntradaCaixa getMotivoEntradaCaixa(int cod) {
		return MotivoEntradaCaixaDao.getMotivoEntradaCaixa(cod);
	}

	public static void excluir(int cod) {
		MotivoEntradaCaixaDao.excluirMotivoEntradaCaixa(cod);
	}
	

}
