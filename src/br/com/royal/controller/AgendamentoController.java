package br.com.royal.controller;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.royal.dao.AgendamentoDao;
import br.com.royal.model.Agendamento;

public class AgendamentoController {
	
	public void addAgendamento(Agendamento a){
		AgendamentoDao.inserir(a);
	}
	
	public ArrayList<Agendamento> listarAgendamentos(Calendar data){
		try{
			return AgendamentoDao.listar(data);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public void editaAgendamento(Agendamento a){
		AgendamentoDao.editar(a);
	}
	
	public void removeAgendamento (Agendamento a){
		AgendamentoDao.excluir(a);
	}
	
	public Agendamento encontraInfoAgendamento (Agendamento a, Calendar data)
	{
		try{
			return AgendamentoDao.encontraInfoAgendamento(a, data);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	public ArrayList<Agendamento> verificaIntervaloAgendamentos(Agendamento a){
		try{
			return AgendamentoDao.verificaIntervaloAgendamentos(a);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
