package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.ComandaDao;
import br.com.royal.model.Cliente;
import br.com.royal.model.Produto;
import br.com.royal.model.Servico;
import br.com.royal.model.ServicoComanda;

public class ComandaController {

	public static ArrayList<Cliente> getClientes() {
		return ComandaDao.getClientes();
	}

	public static ArrayList<Cliente> getClientes(String nome) {
		return ComandaDao.getClientes(nome);
	}

	public static Cliente getCliente(int cod) {
		return ComandaDao.getCliente(cod);
	}

}
