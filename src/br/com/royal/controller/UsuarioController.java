package br.com.royal.controller;

import java.util.ArrayList;

import br.com.royal.dao.UsuarioDao;
import br.com.royal.model.Funcionario;
import br.com.royal.model.Usuario;

public class UsuarioController {
	private static Usuario usuarioLogado;
	
	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	public static void setUsuarioLogado(Usuario usuarioLogado) {
		UsuarioController.usuarioLogado = usuarioLogado;
	}
	
	public static Funcionario getFuncionario(int cod){
		return UsuarioDao.getFuncionario(cod);
	}
	
	public static ArrayList<Funcionario> getFuncionarios(){
		return UsuarioDao.getFuncionarios();
	}
	
	public static void cadastrarUsuario(Usuario u){
		UsuarioDao.cadastroUsuario(u);
	}
	
	public static void editarUsuario(Usuario u, int cod) {
		UsuarioDao.editarUsuario(u, cod);		
	}
	
	public static void excluirUsuario(int cod){
		UsuarioDao.excluirCliente(cod);
	}
	
	public static ArrayList<Usuario> getUsuarios(){
		return UsuarioDao.getUsuarios();
	}
	
	public static ArrayList<Usuario> getUsuarios(String login){
		return UsuarioDao.getUsuarios(login);
	}

	public static Usuario getUsuario(int cod) {
		return UsuarioDao.getUsuario(cod);
	}
	
	public static Usuario getUsuario(String login) {
		return UsuarioDao.getUsuario(login);
	}
	
	public static boolean verificaDuplicidadeLogin(String login, int cod){
		if(login.equals(UsuarioDao.getLoginUsuario(login,cod)))
			return true;
		return false;
	}

	public static ArrayList<Funcionario> getAlgunsFuncionarios(){
		return UsuarioDao.getAlgunsFuncionarios();
	}
}
