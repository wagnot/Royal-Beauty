package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.ServicoDao;
import br.com.royal.model.Funcionario;
import br.com.royal.model.Servico;


public class ServicoController {
	public static void cadastrarServico(Servico s){
		ServicoDao.cadastrarServico(s);
	}
	
	public static void editarServico(Servico s, int cod){
		ServicoDao.editarServico(s, cod);
	}
	
	public static void excluirServico(int cod){
		ServicoDao.excluirServico(cod);
	}

	public static ArrayList<Servico> getServicos(String nomeServico) {
		return ServicoDao.getServicos(nomeServico);
	}
	
	public static ArrayList<Servico> getServicosPorFuncionario(Funcionario f){
		return ServicoDao.getServicosPorFuncionario(f);
	}
	
	public static ArrayList<Servico> getServicos() {
		return ServicoDao.getServicos();
	}

	public static Servico getServico(int cod) {
		return ServicoDao.getServico(cod);
	}
	
	public static boolean verificaDuplicidadeNome(String nome, int cod){
		if(nome.equals(ServicoDao.getNomeServico(nome, cod)))
			return true;
		return false;
	}
}
