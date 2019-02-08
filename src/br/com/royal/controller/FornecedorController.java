package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.FornecedorDao;
import br.com.royal.model.Fornecedor;

public class FornecedorController {
	private FornecedorDao foDao = new FornecedorDao();

	public List<Fornecedor> listar() {
		return foDao.listar();
	}

	public List<Fornecedor> listar(String nome) {
		return foDao.listar(nome);
	}

	public void excluirFornecedor(String cpf, String cnpj) {
		foDao.excluirFornecedor(cpf, cnpj);

	}

	public boolean inserir(Fornecedor f, String cnpj, String cpf) {
		if (foDao.conferirCpf(f.getCpfFornecedor(), cpf)
				|| foDao.conferirCnpj(f.getCnpjFornecedor(), cnpj)) {
			foDao.cadastrarFornecedor(f);
			return true;
		} else {
			return false;
		}

	}

	public boolean conferirCpf(String cpf, String cpfTabela) {
		if (foDao.conferirCpf(cpf, cpfTabela)) {

			return true;
		} else {
			return false;
		}

	}

	public boolean conferirCnpj(String cnpj, String cnpjTabela) {
		if (foDao.conferirCnpj(cnpj, cnpjTabela)) {

			return true;
		} else {
			return false;
		}

	}

	public boolean editar(Fornecedor f, String cpf, String cnpj) {
		if (f.getCpfFornecedor().equals(cpf)
				&& f.getCnpjFornecedor().equals(cnpj)) {
			foDao.editarFornecedor(f, cpf, cnpj);
			return true;
		}
		if (foDao.conferirCpf(f.getCpfFornecedor(), cpf)
				|| foDao.conferirCnpj(f.getCnpjFornecedor(), cnpj)) {
			foDao.editarFornecedor(f, cpf, cnpj);
			;
			return true;
		} else {
			return false;
		}

	}

}
