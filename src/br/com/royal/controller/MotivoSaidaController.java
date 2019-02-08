package br.com.royal.controller;

import java.util.List;

import br.com.royal.dao.MotivoEntradaDao;
import br.com.royal.dao.MotivoSaidaDao;
import br.com.royal.model.MotivoEntrada;
import br.com.royal.model.MotivoSaida;

public class MotivoSaidaController {
	
	
	
	private MotivoSaidaDao msDao = new MotivoSaidaDao();

	public List<MotivoSaida> listar() {
		return msDao.listar();
	}

	public List<MotivoSaida> achou(String nome) {
		return msDao.find(nome);
	}

	public boolean inserir(MotivoSaida me) {
		if (msDao.conferirMotivo(me)) {
			msDao.inserirMotivo(me);
			return true;
		} else {
			return false;
		}

	}

	public MotivoSaida inserirJcombo(MotivoSaida me) {
		return msDao.inserirJcombo(me);
	}
	public MotivoSaida pegarMotivo (String descricao){
		return msDao.pegarMotivo(descricao);
	}

	public boolean editarMotivo(MotivoSaida me, String descricao) {
		if (me.getDescricaoMotivoSaida().equals(descricao)) {
			msDao.editar(me, descricao);
			return true;
		}

		if (msDao.conferirMotivo(me)) {
			msDao.editar(me, descricao);
			return true;
		} else {
			return false;
		}

	}
	
	
	
	
	
	

}
