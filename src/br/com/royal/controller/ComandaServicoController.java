package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.ComandaDao;
import br.com.royal.dao.ComandaServicoDao;
import br.com.royal.model.ComandaServico;
import br.com.royal.model.Servico;
import br.com.royal.model.ServicoComanda;

public class ComandaServicoController {
	public static ArrayList<Servico> getServicos() {
		return ComandaServicoDao.getServicos();
	}

	public static ArrayList<Servico> getServicos(ArrayList<ServicoComanda> nomes) {
		return ComandaServicoDao.getServicosLimitados(nomes);
	}
	
	public static ArrayList<Servico> getServicosPComanda(ComandaServico cs){
		return ComandaServicoDao.getServicosPComanda(cs);
	}
	
	public static ArrayList<ComandaServico> getComandasServico(){
		return ComandaServicoDao.listar();
	}
	
	public static ArrayList<Servico> getServicos(String nomeServico,
			ArrayList<ServicoComanda> nomes) {
		return ComandaServicoDao.getServicosLimitados(nomeServico, nomes);
	}

	public static ArrayList<Servico> getServicos(String nomeServico) {
		return ComandaServicoDao.getServicos(nomeServico);
	}

	public static Servico getServico(int cod) {
		return ComandaServicoDao.getServico(cod);
	}

	public static Servico getServico(String nome) {
		return ComandaServicoDao.getServico(nome);
	}

	public static ArrayList<String> getNomeServicos() {
		ArrayList<String> lstString = new ArrayList<String>();
		for (Servico s : getServicos()) {
			lstString.add(s.getNomeServico().toLowerCase());
		}
		return lstString;
	}
	
	public static int cadastrarComandaServico(ComandaServico comanda){
		return ComandaServicoDao.cadastrarComandaServico(comanda);
	}
}
