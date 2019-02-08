package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.MotivoSaidaCaixaDao;
import br.com.royal.model.MotivoSaidaCaixa;

public class MotivoSaidaCaixaController {
	public static ArrayList<MotivoSaidaCaixa> getMotivoSaidaCaixas() {
		return MotivoSaidaCaixaDao.getMotivoSaidaCaixas();
	}
	
	public static ArrayList<MotivoSaidaCaixa> getMotivoSaidaCaixas(String nome) {
		return MotivoSaidaCaixaDao.getMotivoSaidaCaixas(nome);
	}

	public static boolean conferirDuplicidadeDescricao(String descricao, int cod){
		if(descricao.equals(MotivoSaidaCaixaDao.getDescricaoMotivoSaidaCaixa(descricao, cod)))
			return true;
		return false;
	}

	public static void cadastrar(MotivoSaidaCaixa fp) {
		MotivoSaidaCaixaDao.cadastrarMotivoSaidaCaixa(fp);
	}

	public static void editar(MotivoSaidaCaixa fp, int cod) {
		MotivoSaidaCaixaDao.editarMotivoSaidaCaixa(fp, cod);
	}

	public static MotivoSaidaCaixa getMotivoSaidaCaixa(int cod) {
		return MotivoSaidaCaixaDao.getMotivoSaidaCaixa(cod);
	}

	public static void excluir(int cod) {
		MotivoSaidaCaixaDao.excluirMotivoSaidaCaixa(cod);
	}
}
