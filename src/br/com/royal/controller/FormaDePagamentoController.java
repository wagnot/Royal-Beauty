package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.FormaDePagamentoDao;
import br.com.royal.model.FormaDePagamento;

public class FormaDePagamentoController {

	public static ArrayList<FormaDePagamento> getFormasDePagamento() {
		return FormaDePagamentoDao.getFormasDePagamento();
	}
	
	public static ArrayList<FormaDePagamento> getFormasDePagamento(String nome) {
		return FormaDePagamentoDao.getFormasDePagamento(nome);
	}

	public static boolean conferirDuplicidadeDescricao(String descricao, int cod){
		if(descricao.equals(FormaDePagamentoDao.getDescricaoFormaDePagamento(descricao, cod)))
			return true;
		return false;
	}

	public static void cadastrar(FormaDePagamento fp) {
		FormaDePagamentoDao.cadastrarFormaDePagamento(fp);
	}

	public static void editar(FormaDePagamento fp, int cod) {
		FormaDePagamentoDao.editarFormaDePagamento(fp, cod);
	}

	public static FormaDePagamento getFormaDePagamento(int codFormaDePagamento) {
		return FormaDePagamentoDao.getFormaDePagamento(codFormaDePagamento);
	}

	public static void excluir(int cod) {
		FormaDePagamentoDao.excluirFormaDePagamento(cod);
		
	}
	
}
