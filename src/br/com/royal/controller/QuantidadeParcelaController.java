package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.QuantidadeParcelaDao;
import br.com.royal.model.QuantidadeParcela;


public class QuantidadeParcelaController {

	public static ArrayList<QuantidadeParcela> getQuantidadeParcelas() {
		return QuantidadeParcelaDao.getQuantidadeParcelas();
	}
	
	public static ArrayList<QuantidadeParcela> getQuantidadeParcelas(String nome) {
		return QuantidadeParcelaDao.getQuantidadeParcelas(nome);
	}

	public static boolean conferirDuplicidadeDescricao(String descricao, int cod){
		if(descricao.equals(QuantidadeParcelaDao.getDescricaoQuantidadeParcela(descricao, cod)))
			return true;
		return false;
	}

	public static void cadastrar(QuantidadeParcela fp) {
		QuantidadeParcelaDao.cadastrarQuantidadeParcela(fp);
	}

	public static void editar(QuantidadeParcela fp, int cod) {
		QuantidadeParcelaDao.editarQuantidadeParcela(fp, cod);
	}

	public static QuantidadeParcela getQuantidadeParcela(int codFormaDePagamento) {
		return QuantidadeParcelaDao.getQuantidadeParcela(codFormaDePagamento);
	}

	public static void excluir(int cod) {
		QuantidadeParcelaDao.excluirQuantidadeParcela(cod);
		
	}
	
}
