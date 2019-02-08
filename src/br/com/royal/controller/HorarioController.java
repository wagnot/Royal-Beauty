package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.HorarioDao;
import br.com.royal.model.Horario;

public class HorarioController {
	public void inserir(Horario h) {
		HorarioDao.inserir(h);
	}

	public Horario buscaIdHorario (Horario h) {
		return HorarioDao.buscaIDHorario(h);
	}
	
	public ArrayList <Horario> listarHorariosGerais(){
		return HorarioDao.listarGerais();
	}
	
	public ArrayList <Horario> listarHorariosNaoEspecificos (){
		return HorarioDao.listarNaoEspecificos();
	}
	
	public ArrayList <Horario> listarHorariosEspecificos (Horario h){
		return HorarioDao.listarEspecificos(h);
	}
	
	public void editarHorario(Horario h){
		HorarioDao.editar(h);
	}
	
	public boolean verificaDuplicidadeNaEdicao(Horario h){
		if (HorarioDao.verificaDuplicidadeNaEdicao(h)!=null)
			return true;
		else
			return false;
	}
	
	public boolean jaExiste(Horario h){
		if (HorarioDao.jaExiste(h)!=null)
			return true;
		else
			return false;
	}
	
	public void setaHorarioPraTodasAsDatas(Horario h){
		HorarioDao.setaHorarioPraTodasAsDatas(h);
	}
	
	public Horario verificaSubstituicao(Horario h){
		return HorarioDao.verificaSubstituicao(h);
	}
	
	public ArrayList<Horario> pesquisaHorario(String horario){
		return HorarioDao.pesquisaHorario(horario);
	}
	
	public void excluirHorario(Horario h){
		HorarioDao.excluir(h);
	}
	
	public Horario encontreHorarioPorID(Horario h){
		try{
			return HorarioDao.encontrePorID(h);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public Horario jaExisteInativo(Horario h){
		return HorarioDao.jaExisteInativo(h);
	}
	
	public void reativaHorario(Horario h){
		HorarioDao.reativaHorario(h);
	}
}
