package br.com.royal.controller;

import java.io.File;
import java.util.ArrayList;

import br.com.royal.dao.ClienteDao;
import br.com.royal.model.Cliente;

public class ClienteController {

	public boolean telefoneUpdate(Object[] lista, String telefone) {
		for (Object t : lista) {
			if (t.toString().equals(telefone))
				return true;
		}
		return false;
	}

	public static boolean cadastrarCliente(Cliente c) {
		if (c != null) {
			ClienteDao.cadastrarCliente(c);
			return true;
		}
		return false;
	}

	public static boolean editarCliente(Cliente c) {
		if (c != null) {
			ClienteDao.editarCliente(c);
			return true;
		}
		return false;
	}

	public static void excluirCliente(int cod) {
		ClienteDao.excluirCliente(cod);
	}

	public static boolean verificaDuplicidadeCpf(String cpf, int cod) {
		if (ClienteDao.getCpf(cpf, cod).equals(cpf))
			return true;
		return false;
	}

	public static boolean verificaDuplicidadeEmail(String email, int cod) {
		if (ClienteDao.getEmail(email, cod).equals(email))
			return true;
		return false;
	}
	
	public static ArrayList<Cliente> getClientes() {
		return ClienteDao.getClientes();
	}

	public static ArrayList<Cliente> getClientes(String nome) {
		return ClienteDao.getClientes(nome);
	}

	public static Cliente getCliente(int cod) {
		return ClienteDao.getCliente(cod);
	}
	
	public static boolean deletaImagem(String local){
		File file = new File(local);
		return file.delete();
	}
}
