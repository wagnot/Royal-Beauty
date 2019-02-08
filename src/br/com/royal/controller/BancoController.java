package br.com.royal.controller;

import br.com.royal.dao.LoginDao;
import br.com.royal.model.Login;

public class BancoController {

	private LoginDao lDao = new LoginDao();

	public boolean loginUser(Login l) {
		if (l.getUser().length() > 0 && l.getSenha().length() > 0
				&& lDao.logar(l) != null) {			
			if (lDao.logar(l).getUser().equals(l.getUser())
					&& lDao.logar(l).getSenha().equals(l.getSenha()))
				return true;
		}
		return false;

	}

	public boolean recoveryUser(Login l) {
		if (l.getSenha().length() <= 1) {
			return false;
		} else {
			
			lDao.recuperar(l);
			return true;
		}

	}
	public boolean verificaLogin(String login){
		if(login.equals(LoginDao.getUsuario(login).getNome())){
			return true;
		}
		return false;
	}
	
	public Login getUsers(String login){	
		return LoginDao.getUsuario(login);
	}
	
	public static void editarUsuario(Login l){
		LoginDao.editarUsuario(l);
	}
}
