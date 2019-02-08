package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.FabricanteDao;
import br.com.royal.model.Fabricante;

public class FabricanteController {

	private FabricanteDao fDao = new FabricanteDao();

	public List<Fabricante> listar() {
		return fDao.listar();
	}

	public List<Fabricante> achou(String nome) {
		return fDao.find(nome);
	}

	public Fabricante inserirJcombo(Fabricante f) {
		return fDao.inserirJcombo(f);
	}

	public boolean inserir(Fabricante me) {
		if (fDao.conferirNome(me)) {
			fDao.inserirFabricante(me);
			return true;
		} else {
			return false;
		}

	}

	public boolean editarFabricante(Fabricante me, String nome) {
		if (me.getNomeFabricante().equals(nome)) {
			fDao.editar(me, nome);
			return true;
		}

		if (fDao.conferirNome(me)) {
			fDao.editar(me, nome);
			return true;
		} else {
			return false;
		}

	}

}
