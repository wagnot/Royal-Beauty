package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.FuncionarioDao;
import br.com.royal.model.Funcionario;
import br.com.royal.model.Servico;
import br.com.royal.model.Usuario;

public class FuncionarioController {

	public static void cadastrarFuncionario(Funcionario f) {
		FuncionarioDao.cadastrarFuncionario(f);
	}

	public static void excluirFuncionario(int cod) {
		FuncionarioDao.excluirFuncionario(cod);
	}

	public static boolean editarFuncionario(Funcionario f, int cod) {
		if (f != null) {
			FuncionarioDao.editarFuncionario(f, cod);
			return true;
		}
		return false;
	}
	
	public static ArrayList<Funcionario> getFuncionarios(){
		return FuncionarioDao.getFuncionarios();
	}
	
	public static ArrayList<Funcionario> getFuncionarios(String nome){
		return FuncionarioDao.getFuncionarios(nome);
	}
	
	public static Funcionario getFuncionario(int cod){
		return FuncionarioDao.getFuncionario(cod);
	}
	
	public static boolean verificaDuplicidadeCpf(String cpf, int cod){
		if(FuncionarioDao.getCpf(cpf, cod).equals(cpf))
			return true;
		return false;
	}
	
	public static boolean verificaDuplicidadeEmail(String email, int cod){
		if(FuncionarioDao.getEmail(email, cod).equals(email))
			return true;
		return false;
	}
	
	public static boolean verificaDuplicidadePis(String pis){
		if(FuncionarioDao.getPis(pis).equals(pis))
			return true;
		return false;
	}
	
	public static boolean validaPis(String pis){
		if(Validacoes.validaPIS(pis))
				return true;
		return false;
	}
	
	public static boolean validaCpf(String cpf){
		if(Validacoes.validaCpf(cpf))
				return true;
		return false;
	}
	
	public static boolean validaDDI(String ddi){
		if(Validacoes.verificaDDITelefone(ddi))
				return true;
		return false;
	}
	
	public static boolean validaData(String data){
		if(Validacoes.validaData(data.replaceAll("[_/]", "")))
				return true;
		return false;
	}
	
	public static ArrayList<Servico> getServicos(){
		return FuncionarioDao.getServicos();
	}
	
	public static Usuario verificaUsuarioExistente(int cod){
		if(FuncionarioDao.verificaUsuarioExistente(cod)!=null)
			return FuncionarioDao.verificaUsuarioExistente(cod);
		return null;
	}
}
