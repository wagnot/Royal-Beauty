package br.com.royal.controller;


import java.util.ArrayList;

import br.com.royal.dao.FiadoDao;
import br.com.royal.model.Fiado;

public class FiadoController {
	private FiadoDao fDao = new FiadoDao();
	
	public void cadastrarFiado(Fiado f) {
		fDao.cadastrarFiado(f);
	}
	
	public ArrayList<Fiado> getTodosFiados(){
		return fDao.listarFiados();
	}
	
	public ArrayList<Fiado> getTodosFiadosDia(String data){
		return fDao.listarFiadosDia(data);
	}
}
