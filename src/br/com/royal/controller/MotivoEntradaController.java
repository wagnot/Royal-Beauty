package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.MotivoEntradaDao;
import br.com.royal.model.Fabricante;
import br.com.royal.model.MotivoEntrada;

public class MotivoEntradaController {
	private MotivoEntradaDao meDao = new MotivoEntradaDao();

	public List<MotivoEntrada> listar() {
		return meDao.listar();
	}

	public List<MotivoEntrada> achou(String nome) {
		return meDao.find(nome);
	}

	public boolean inserir(MotivoEntrada me) {
		if (meDao.conferirMotivo(me)) {
			meDao.inserirMotivo(me);
			return true;
		} else {
			return false;
		}

	}

	public MotivoEntrada inserirJcombo(MotivoEntrada me) {
		return meDao.inserirJcombo(me);
	}

	public boolean editarMotivo(MotivoEntrada me, String descricao) {
		if (me.getDescricao().equals(descricao)) {
			meDao.editar(me, descricao);
			return true;
		}

		if (meDao.conferirMotivo(me)) {
			meDao.editar(me, descricao);
			return true;
		} else {
			return false;
		}

	}

}
